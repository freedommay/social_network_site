package edu.zju.cst.demo.service;

import edu.zju.cst.demo.DemoApplication;
import edu.zju.cst.demo.model.Question;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SearchServiceTest {

    @Autowired
    SearchService searchService;

    @Test
    public void testSearchQuestion() throws IOException, SolrServerException {
        List<Question> questionList = searchService.searchQuestion("Content", 0, 50, "<em>", "</em>");
        for (Question question: questionList) {
            System.out.println(question.getId());
        }
    }
}
