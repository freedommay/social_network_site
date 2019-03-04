package edu.zju.cst.demo.async.handler;

import edu.zju.cst.demo.async.EventHandler;
import edu.zju.cst.demo.async.EventModel;
import edu.zju.cst.demo.async.EventType;
import edu.zju.cst.demo.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AddQuestionHandler implements EventHandler {

    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);

    @Autowired
    SearchService searchService;

    @Override
    public void doHandle(EventModel eventModel) {
        try {
            searchService.indexQuestion(eventModel.getEntityID(),
                    eventModel.getExt("title"), eventModel.getExt("content"));
        } catch (Exception e) {
            logger.error("error!");
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }

}
