package edu.zju.cst.demo.service;

import edu.zju.cst.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SensitiveServiceTest {
    @Autowired
    SensitiveService sensitiveService;

    @Test
    public void testSensitiveWords() {
        sensitiveService.addWord("敏感词");
        sensitiveService.addWord("赌博");
        String input = "这是一段带有敏感词的语句， 例如赌博  ";
        String output = "这是一段带有***的语句， 例如***  ";
        assertEquals(output, sensitiveService.filter(input));
    }
}
