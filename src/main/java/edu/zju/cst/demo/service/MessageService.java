package edu.zju.cst.demo.service;

import edu.zju.cst.demo.dao.MessageDAO;
import edu.zju.cst.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationID, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationID, offset, limit);
    }

    public List<Message> getConversationList(int userID, int offset, int limit) {
        return messageDAO.getConversationList(userID, offset, limit);
    }

    public int getConversationUnreadCount(int userID, String conversationID) {
        return messageDAO.getConversationUnreadCount(userID, conversationID);
    }
}
