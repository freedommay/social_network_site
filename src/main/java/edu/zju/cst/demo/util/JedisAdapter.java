package edu.zju.cst.demo.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class JedisAdapter implements InitializingBean {

    private JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        jedisPool = new JedisPool(config, "localhost", 6379, 2000, "redis");
    }

    public long sadd(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long srem(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srem(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long scard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public long lpush(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 当count=0时，删除key中所有值为value的元素
    public long lrem(String key, long count, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrem(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> brpop(int timeout, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.brpop(timeout, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> lrange(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public long zadd(String key, double score, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long zrem(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrem(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            tx.discard();
        } finally {
            if (tx != null) {
                try {
                    tx.close();
                } catch (IOException ioe) {
                }
            }
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrange(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public long zcard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcard(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Double zscore(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
