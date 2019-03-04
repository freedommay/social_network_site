package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.async.EventModel;
import edu.zju.cst.demo.async.EventProducer;
import edu.zju.cst.demo.async.EventType;
import edu.zju.cst.demo.model.*;
import edu.zju.cst.demo.service.*;
import edu.zju.cst.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    FollowService followService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question(title, content, new Date(), hostHolder.getUser().getId(), 0);
            if (questionService.addQuestion(question) > 0) {
                eventProducer.fireEvent(new EventModel(EventType.ADD_QUESTION)
                        .setActorID(question.getUserID()).setEntityID(question.getId())
                        .setExt("title", question.getTitle()).setExt("content", question.getContent()));
                return Utils.getJSONString(0);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.selectByID(qid);
        model.addAttribute("question", question);
        List<Integer> userIDs = followService.getFollowers(EntityType.ENTITY_QUESTION, qid, 10);
        List<User> users = new ArrayList<>();
        for (int userID : userIDs) {
            User user = userService.getUser(userID);
            users.add(user);
        }
        model.addAttribute("users", users);
        model.addAttribute("size", users.size());
        boolean isFollower = false;
        if (hostHolder.getUser() != null) {
            isFollower = followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, qid);
        }
        model.addAttribute("follow", isFollower);
        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            if (hostHolder.getUser() == null) {
                vo.set("liked", 0);
            } else {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }
            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            vo.set("user", userService.getUser(comment.getUserID()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "detail";
    }
}
