package edu.zju.cst.demo.async.handler;

import com.alibaba.fastjson.JSONObject;
import edu.zju.cst.demo.async.EventHandler;
import edu.zju.cst.demo.async.EventModel;
import edu.zju.cst.demo.async.EventType;
import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.Feed;
import edu.zju.cst.demo.model.Question;
import edu.zju.cst.demo.model.User;
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

    @Override
    public void doHandle(EventModel eventModel) {
        Random random = new Random();
        eventModel.setActorID(11 + random.nextInt(10));
        Feed feed = new Feed();
        feed.setUserID(eventModel.getActorID());
        feed.setCreatedDate(new Date());
        feed.setData(buildFeedData(eventModel));
        feed.setType(eventModel.getType().getValue());
        if (feed.getData() == null) {
            return;
        }
        feedService.addFeed(feed);

        // 给事件的粉丝推送
        List<Integer> followers = followService.getFollowers(EntityType.ENTITY_USER, eventModel.getActorID(), Integer.MAX_VALUE);
        followers.add(0);
        for (int follower: followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
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
                (eventModel.getType() == EventType.FOLLOW  && eventModel.getEntityType() == EntityType.ENTITY_QUESTION)) {
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
        return Arrays.asList(EventType.COMMENT, EventType.FOLLOW);
    }
}
