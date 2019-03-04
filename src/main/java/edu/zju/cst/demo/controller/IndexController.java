package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.HostHolder;
import edu.zju.cst.demo.model.Question;
import edu.zju.cst.demo.model.ViewObject;
import edu.zju.cst.demo.service.FollowService;
import edu.zju.cst.demo.service.QuestionService;
import edu.zju.cst.demo.service.UserService;
import edu.zju.cst.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private static final String OFFSET = "" + "0";

    private static final String LIMIT = "" + "10";

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
                viewObject.set("current", hostHolder.getUser());
            }
            viewObject.set("followed", isFollowed);

            viewObjects.add(viewObject);
        }
        return viewObjects;
    }



    @RequestMapping(path = {"/", "/index"})
//    @ResponseBody
    public String index(Model model,
                        @RequestParam(value = "offset", defaultValue = OFFSET) int offset,
                        @RequestParam(value = "limit", defaultValue = LIMIT) int limit) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
//        List<ViewObject> vos = getQuestions(0, offset, limit);
        return "index";
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    public List<ViewObject> more(@RequestParam(value = "offset", defaultValue = OFFSET) int offset,
                       @RequestParam(value = "limit", defaultValue = LIMIT) int limit) {
        List<ViewObject> vos = getQuestions(0, offset, limit);
        return vos;
    }


    @RequestMapping(path = {"/user/{userID}"}, method = {RequestMethod.GET})
    public String userIndex(Model model, @PathVariable("userID") int userID) {
        model.addAttribute("vos", getQuestions(userID, 0, 10));
        return "index";
    }
}
