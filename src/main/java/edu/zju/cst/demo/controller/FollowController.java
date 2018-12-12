package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.async.EventModel;
import edu.zju.cst.demo.async.EventProducer;
import edu.zju.cst.demo.async.EventType;
import edu.zju.cst.demo.model.*;
import edu.zju.cst.demo.service.CommentService;
import edu.zju.cst.demo.service.FollowService;
import edu.zju.cst.demo.service.QuestionService;
import edu.zju.cst.demo.service.UserService;
import edu.zju.cst.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    FollowService followService;

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/followUser"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userID") int userID) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userID);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorID(hostHolder.getUser().getId()).setEntityID(userID)
                .setEntityType(EntityType.ENTITY_USER).setEntityOwnerID(userID));

        return Utils.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"/unfollowUser"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@RequestParam("userID") int userID) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userID);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorID(hostHolder.getUser().getId()).setEntityID(userID)
                .setEntityType(EntityType.ENTITY_USER).setEntityOwnerID(userID));

        return Utils.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"/followQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@RequestParam("questionID") int questionID) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }

        Question q = questionService.selectByID(questionID);
        if (q == null) {
            return Utils.getJSONString(1, "问题不存在");
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionID);

        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorID(hostHolder.getUser().getId()).setEntityID(questionID)
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityOwnerID(q.getUserID()));

        Map<String, Object> info = new HashMap<>();
        info.put("headURL", hostHolder.getUser().getHeadURL());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionID));
        return Utils.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(path = {"/unfollowQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionID") int questionID) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }

        Question q = questionService.selectByID(questionID);
        if (q == null) {
            return Utils.getJSONString(1, "问题不存在");
        }

        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionID);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorID(hostHolder.getUser().getId()).setEntityID(questionID)
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityOwnerID(q.getUserID()));

        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.ENTITY_QUESTION, questionID));
        return Utils.getJSONString(ret ? 0 : 1, info);
    }

    private List<ViewObject> getUsersInfo(int localUserID, List<Integer> userIDs) {
        List<ViewObject> userInfos = new ArrayList<>();
        for (Integer uid : userIDs) {
            User user = userService.getUser(uid);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("commentCount", commentService.getUserCommentCount(uid));
            vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, uid));
            vo.set("followeeCount", followService.getFolloweeCount(uid, EntityType.ENTITY_USER));
            if (localUserID != 0) {
                vo.set("followed", followService.isFollower(localUserID, EntityType.ENTITY_USER, uid));
            } else {
                vo.set("followed", false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }

    /* 该用户被哪些用户所关注 */
    @RequestMapping(path = {"/user/{uid}/followers"}, method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("uid") int userId) {
        List<Integer> followerIds = followService.getFollowers(EntityType.ENTITY_USER, userId, 0, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followerIds));
        } else {
            model.addAttribute("followers", getUsersInfo(0, followerIds));
        }
        model.addAttribute("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.getUser(userId));
        return "followers";
    }

    /* 该用户关注了哪些用户 */
    @RequestMapping(path = {"/user/{uid}/followees"}, method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("uid") int userID) {
        List<Integer> followeeIds = followService.getFollowees(userID, EntityType.ENTITY_USER, 0, 10);

        if (hostHolder.getUser() != null) {
            model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followeeIds));
        } else {
            model.addAttribute("followees", getUsersInfo(0, followeeIds));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(userID, EntityType.ENTITY_USER));
        model.addAttribute("curUser", userService.getUser(userID));
        return "followees";
    }
}
