package edu.zju.cst.demo;

import edu.zju.cst.demo.dao.QuestionDAO;
import edu.zju.cst.demo.dao.UserDAO;
import edu.zju.cst.demo.model.EntityType;
import edu.zju.cst.demo.model.Question;
import edu.zju.cst.demo.model.User;
import edu.zju.cst.demo.service.FollowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class InitDatabaseTest {
    private Random random = new Random();
    private final int MAX_USER = 10;
    private final int MAX_QUESTION = 10;

    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    FollowService followService;

    @Test
    public void testUserDAO() {
        for (int i = 0; i < MAX_USER; i++) {
            String name = String.format("USER_%d", i);
            String password = "";
            String salt = "";
            String headURL = String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000));
            User user = new User(name, password, salt, headURL);
            userDAO.addUser(user);
        }
    }

    @Test
    public void testFollowService() {
        for(int i = 11; i <= 20; i++) {
            for (int j = 11; j <= i ; j++) {
                followService.follow(j, EntityType.ENTITY_USER, i);
            }
        }
    }

    @Test
    public void testQuestionDAO() {
        for (int i = 0; i < MAX_QUESTION; i++) {
            String title = String.format("TITLE_%d", i);
            String content = String.format("CONTENT_Blablabla_%d", i);
            Date createdDate = new Date();
            int userID = i + 1;
            int commentCount = i;
            Question question = new Question(title, content, createdDate, userID, commentCount);
            questionDAO.addQuestion(question);
        }
    }
}
