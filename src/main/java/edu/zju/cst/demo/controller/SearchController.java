package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.Question;
import edu.zju.cst.demo.model.ViewObject;
import edu.zju.cst.demo.service.FollowService;
import edu.zju.cst.demo.service.QuestionService;
import edu.zju.cst.demo.service.SearchService;
import edu.zju.cst.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    SearchService searchService;

    @Autowired
    QuestionService questionService;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count,
                    "<em>", "</em>");
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q = questionService.selectByID((question.getId()));
                ViewObject vo = new ViewObject();
                if (question.getContent() != null) {
                    q.setContent(question.getContent());
                    System.out.println(q.getContent());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
                vo.set("user", userService.getUser(q.getUserID()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("error!" + e.getMessage());
        }
        return "result";
    }
}
