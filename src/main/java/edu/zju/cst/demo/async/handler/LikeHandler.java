package edu.zju.cst.demo.async.handler;

import edu.zju.cst.demo.async.EventHandler;
import edu.zju.cst.demo.async.EventModel;
import edu.zju.cst.demo.async.EventType;
import edu.zju.cst.demo.model.Message;
import edu.zju.cst.demo.model.User;
import edu.zju.cst.demo.service.MessageService;
import edu.zju.cst.demo.service.UserService;
import edu.zju.cst.demo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel eventModel) {
        User user = userService.getUser(eventModel.getActorID());
        String content = "用户" + user.getName() + "赞了你的评论";
        Message message = new Message(Utils.SYSTEM_USERID, eventModel.getEntityOwnerID(), content, 0,  new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
