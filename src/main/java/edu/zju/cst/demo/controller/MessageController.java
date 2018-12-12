package edu.zju.cst.demo.controller;

import edu.zju.cst.demo.model.HostHolder;
import edu.zju.cst.demo.model.Message;
import edu.zju.cst.demo.model.User;
import edu.zju.cst.demo.model.ViewObject;
import edu.zju.cst.demo.service.MessageService;
import edu.zju.cst.demo.service.UserService;
import edu.zju.cst.demo.util.Utils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        try {
            int userID = hostHolder.getUser().getId();
            List<ViewObject> vos = new ArrayList<>();
            List<Message> messages = messageService.getConversationList(userID, 0, 10);
            for (Message message: messages) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                int targetID = message.getFromID() == userID ? message.getToID() : message.getFromID();
                User user = userService.getUser(targetID);
                vo.set("user", user);
                vo.set("unread", messageService.getConversationUnreadCount(userID, message.getConversationID()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "message";
    }


    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @Param("conversationID") String conversationID) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationID, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message message: conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                User user = userService.getUser(message.getFromID());
                if (user == null) {
                    continue;
                }
                vo.set("headURL", user.getHeadURL());
                vo.set("userID", user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "messageDetail";
    }


    @RequestMapping(path = {"/msg/add"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("id") int toID,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return "redirect:/reglogin";
            }
            int fromID = hostHolder.getUser().getId();
            Message message = new Message(fromID, toID, content, 0, new Date());
            message.setConversationID(fromID, toID);
            messageService.addMessage(message);
            return Utils.getJSONString(0);
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.getJSONString(1, "error");
        }
    }
}
