package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.Feed;
import edu.zju.cst.demo.model.HostHolder;
import edu.zju.cst.demo.service.FeedService;
import edu.zju.cst.demo.service.FollowService;
import edu.zju.cst.demo.service.UserService;
import edu.zju.cst.demo.util.JedisAdapter;
import edu.zju.cst.demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FeedController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;

    @Autowired
    FeedService feedService;

    @Autowired
    UserService userService;

    @Autowired
    JedisAdapter jedisAdapter;

    private List<Feed> getPullFeeds(int localUserID) {
        List<Integer> followees = new ArrayList<>();
            if (localUserID != 0) {
            followees = followService.getFollowees(localUserID, EntityType.ENTITY_USER, Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        return feeds;
    }

    private List<Feed> getPushFeeds(int localUserId) {
        List<String> feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUserId), 0, 10);
        List<Feed> feeds = new ArrayList<>();
        for (String feedId : feedIds) {
            Feed feed = feedService.getById(Integer.parseInt(feedId));
            if (feed != null) {
                feeds.add(feed);
            }
        }
        return feeds;
    }


    // 根据用户最后登录时间，判断是采用推的模式还是拉的模式
    @RequestMapping(path = {"/feeds"}, method = {RequestMethod.GET, RequestMethod.POST})
    private String getFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<Feed> feeds;
        if (new Date().getTime() - userService.getUser(localUserId).getLastLoginTime().getTime() < 24 * 60 * 60 * 1000) {
            feeds = getPushFeeds(localUserId);
        } else {
            feeds = getPullFeeds(localUserId);
        }
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}
