package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.HostHolder;
import edu.zju.cst.demo.model.Question;
import edu.zju.cst.demo.model.ViewObject;
import edu.zju.cst.demo.service.FollowService;
import edu.zju.cst.demo.service.QuestionService;
import edu.zju.cst.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    private List<ViewObject> getQuestions(int userID, int offset, int limit) {
        List<Question> questionList = questionService.getLastedQuestion(userID, offset, limit);
        boolean isFollowed = false;
        List<ViewObject> viewObjects = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUser(question.getUserID()));
            if (hostHolder.getUser() != null) {
                isFollowed = followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, question.getUserID());
            }
            viewObject.set("followed", isFollowed);
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    @RequestMapping(path = {"/", "index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }

    @RequestMapping(path = {"/user/{userID}"}, method = {RequestMethod.GET})
    public String userIndex(Model model, @PathVariable("userID") int userID) {
        model.addAttribute("vos", getQuestions(userID, 0, 10));
        return "index";
    }
}
