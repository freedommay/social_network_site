package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.model.Comment;
import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.HostHolder;
import edu.zju.cst.demo.service.CommentService;
import edu.zju.cst.demo.service.QuestionService;
import edu.zju.cst.demo.service.SensitiveService;
import edu.zju.cst.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class CommentController {
    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    SensitiveService sensitiveService;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
        public String addComment(@RequestParam("questionID") int questionID,
                                @RequestParam("content") String content) {
        try {
            content = HtmlUtils.htmlEscape(content);
            content = sensitiveService.filter(content);
            int userID = -1;
            if (hostHolder.getUser() != null) {
                userID = hostHolder.getUser().getId();
            } else {
                return "redirect:/reglogin";
            }
            Comment comment = new Comment(content, userID, questionID, EntityType.ENTITY_QUESTION, new Date(), 0);
            commentService.addComment(comment);
            int count = commentService.getCommentCount(comment.getEntityID(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityID(), count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/question/" + String.valueOf(questionID);
    }
}
