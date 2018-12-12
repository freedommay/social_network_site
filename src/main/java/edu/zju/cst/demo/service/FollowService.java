package edu.zju.cst.demo.service;

import edu.zju.cst.demo.util.JedisAdapter;
import edu.zju.cst.demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean follow(int userID, int entityType, int entityID) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityID);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userID, entityType);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userID));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityID));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    public boolean unfollow(int userID, int entityType, int entityID) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityID);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userID, entityType);
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zrem(followerKey, String.valueOf(userID));
        tx.zrem(followeeKey, String.valueOf(entityID));
        List<Object> ret = jedisAdapter.exec(tx, jedis);
        return ret.size() == 2 && (Long) ret.get(0) > 0 && (Long) ret.get(1) > 0;
    }

    private List<Integer> getIDsFromSet(Set<String> IDset) {
        List<Integer> IDs = new ArrayList<>();
        for (String str : IDset) {
            IDs.add(Integer.parseInt(str));
        }
        return IDs;
    }

    public List<Integer> getFollowers(int entityType, int entityID, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityID);
        return getIDsFromSet(jedisAdapter.zrevrange(followerKey, 0, count));
    }

    public List<Integer> getFollowers(int entityType, int entityID, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityID);
        return getIDsFromSet(jedisAdapter.zrevrange(followerKey, offset, offset + count));
    }

    public List<Integer> getFollowees(int userID, int entityType, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userID, entityType);
        return getIDsFromSet(jedisAdapter.zrevrange(followeeKey, 0, count));
    }

    public List<Integer> getFollowees(int userID, int entityType, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userID, entityType);
        return getIDsFromSet(jedisAdapter.zrevrange(followeeKey, offset, offset + count));
    }

    public long getFollowerCount(int entityType, int entityID) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityID);
        return jedisAdapter.zcard(followerKey);
    }

    public long getFolloweeCount(int userID, int entityType) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(userID, entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    public boolean isFollower(int userID, int entityType, int entityID) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityID);
        return jedisAdapter.zscore(followerKey, String.valueOf(userID)) != null;
    }
}
