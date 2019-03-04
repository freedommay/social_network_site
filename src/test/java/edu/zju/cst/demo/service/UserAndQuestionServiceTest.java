package edu.zju.cst.demo.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import edu.zju.cst.demo.DemoApplication;
import edu.zju.cst.demo.dao.QuestionDAO;
import edu.zju.cst.demo.dao.UserDAO;
import edu.zju.cst.demo.model.Question;
import edu.zju.cst.demo.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UserAndQuestionServiceTest {

    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    private MongoCollection<Document> userCollection;
    private MongoCollection<Document> questionCollection;

    @Before
    public void setUp() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("weibo");
        questionCollection = database.getCollection("blogs");
        userCollection = database.getCollection("users");
    }

    @Test
    public void insertUserData() {
        try (MongoCursor<Document> cursor = userCollection.find().iterator()) {
            for (int i = 1; i <= 50; i++) {
                Random random = new Random();
                Document result = cursor.next();
                String name = (String) result.get("name");
                if (name == null) {
                    name = "微博用户" + i;
                }
                String password = "password";
                String salt = "salt";
                password = DigestUtils.md5Hex(password + salt);
                String headURL = (String) result.get("avatar");
                if (headURL == null) {
                    headURL = String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000));
                }
                Date lastLoginTime = new Date();
                User user = new User(name, password, salt, headURL, lastLoginTime);
                userDAO.addUser(user);
            }
        }
    }

    @Test
    public void insertQuestionData() throws ParseException {
        try (MongoCursor<Document> cursor = questionCollection.find().iterator()) {
            for (int i = 1; i <= 50; i++) {
                Document result = cursor.next();
                String title = "微博";
                String content = (String) result.get("text");
                int userId = i;
                String sdate = (String) result.get("created_at");
                Date date = new Date();
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sdate);
                } catch (Exception e) {
                    date = new SimpleDateFormat(("yyyy-MM-dd")).parse(sdate);
                }
                int commentCount = (int) result.get("comments_count");
                Question question = new Question(title, content, date, userId, commentCount);
                questionDAO.addQuestion(question);
            }
        }
    }
}
