package edu.zju.cst.demo.async.handler;

import com.alibaba.fastjson.JSONObject;
import edu.zju.cst.demo.async.EventHandler;
import edu.zju.cst.demo.async.EventModel;
import edu.zju.cst.demo.async.EventType;
import edu.zju.cst.demo.model.*;
import edu.zju.cst.demo.service.FeedService;
import edu.zju.cst.demo.service.FollowService;
import edu.zju.cst.demo.service.QuestionService;
import edu.zju.cst.demo.service.UserService;
import edu.zju.cst.demo.util.JedisAdapter;
import edu.zju.cst.demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedHandler implements EventHandler {

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    FollowService followService;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    HostHolder hostHolder;

    @Override
    public void doHandle(EventModel eventModel) { int userId = eventModel.getActorID();

        // 当关注取消时，从TimelineKey中删除该键
        if (eventModel.getType() == EventType.UNFOLLOW && eventModel.getEntityType() == EntityType.ENTITY_USER) {
            String timelineKey = RedisKeyUtil.getTimelineKey(eventModel.getActorID()); // 获取当前用户Feed流列表
            int id = eventModel.getEntityID();    // 获取取消关注的用户id
            // 删除该用户相关的Feed
            List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, Arrays.asList(id), Integer.MAX_VALUE);

            for (Feed f : feeds) {
                jedisAdapter.lrem(timelineKey, 0, String.valueOf(f.getId()));
            }
        } else if (eventModel.getType() != EventType.UNFOLLOW){
            Feed feed = new Feed();
            feed.setUserID(eventModel.getActorID());
            feed.setCreatedDate(new Date());
            feed.setData(buildFeedData(eventModel));
            feed.setType(eventModel.getType().getValue());
            if (feed.getData() == null) {
                return;
            }
            feedService.addFeed(feed);  // 至此可以实现拉的模式

            // 给事件的粉丝推送
            // 如果粉丝在一天之内登陆，则推送
            List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, eventModel.getActorID(), Integer.MAX_VALUE);
            for (int follower : followers) {
                User user = userService.getUser(follower);
                if (new Date().getTime() - user.getLastLoginTime().getTime() < 24 * 60 * 60 * 1000) {
                    String timelineKey = RedisKeyUtil.getTimelineKey(follower);
                    jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
                }
            }
        }
    }

    private String buildFeedData(EventModel eventModel) {
        Map<String, String> map = new HashMap<>();
        User actor = userService.getUser(eventModel.getActorID());
        if (actor == null) {
            return null;
        }
        map.put("userID", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadURL());
        map.put("userName", actor.getName());

        if (eventModel.getType() == EventType.COMMENT ||
                (eventModel.getType() == EventType.FOLLOW && eventModel.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = questionService.selectByID(eventModel.getEntityID());
            if (question == null) {
                return null;
            }
            map.put("questionID", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            map.put("questionContent", question.getContent());
            return JSONObject.toJSONString(map);
        }
        return null;

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMENT, EventType.FOLLOW, EventType.UNFOLLOW);
    }
}
