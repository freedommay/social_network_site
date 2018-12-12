package edu.zju.cst.demo.service;

import edu.zju.cst.demo.dao.QuestionDAO;
import edu.zju.cst.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    QuestionDAO questionDAO;

    public List<Question> getLastedQuestion(int userID, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userID, offset, limit);
    }

    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question);
    }

    public Question selectByID(int id) {
        return questionDAO.selectByID(id);
    }

    public int updateCommentCount(int id, int count) {
        return questionDAO.updateCommentCount(id, count);
    }
}