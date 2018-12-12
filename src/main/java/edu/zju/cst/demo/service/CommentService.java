package edu.zju.cst.demo.service;

import edu.zju.cst.demo.dao.CommentDAO;
import edu.zju.cst.demo.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    public List<Comment> getCommentsByEntity(int entityID, int entityType) {
        return commentDAO.selectByEntity(entityID, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityID, int entityType) {
        return commentDAO.getCommentCount(entityID, entityType);
    }

    public int getUserCommentCount(int userID) {
        return commentDAO.getUserCommentCount((userID));
    }

    public void deleteComment(int entityID, int entityType) {
        commentDAO.updateStatus(entityID, entityType, 1);
    }

    public Comment getCommentByID(int id) {
        return commentDAO.getCommentByID(id);
    }
}
