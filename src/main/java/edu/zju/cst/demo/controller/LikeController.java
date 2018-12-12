package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.async.EventModel;
import edu.zju.cst.demo.async.EventProducer;
import edu.zju.cst.demo.async.EventType;
import edu.zju.cst.demo.model.Comment;
import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.HostHolder;
import edu.zju.cst.demo.service.CommentService;
import edu.zju.cst.demo.service.LikeService;
import edu.zju.cst.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentID") int commentID) {
        if (hostHolder == null) {
            return "redirect:/reglogin";
        }
        Comment comment = commentService.getCommentByID(commentID);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
        .setActorID(hostHolder.getUser().getId()).setEntityID(commentID)
        .setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerID(comment.getUserID())
        .setExt("questionID", String.valueOf(comment.getEntityID())));
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentID);
        return Utils.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentID") int commentID) {
        if (hostHolder == null) {
            return "redirect:/reglogin";
        }
        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentID);
        return Utils.getJSONString(0, String.valueOf(likeCount));
    }
}
