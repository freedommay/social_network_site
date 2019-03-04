package edu.zju.cst.demo.service;

import edu.zju.cst.demo.model.Question;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private static final String SOLR_URL = "http://127.0.0.1:8983/solr";
    private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String QUESTION_TITLE_FIELD = "title";
    private static final String QUESTION_CONTENT_FIELD = "content";

    public List<Question> searchQuestion(String keyword, int offset, int count,
                                         String hlPre, String hlPos) throws IOException, SolrServerException {

        List<Question> questionList = new ArrayList<>();
        final Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", keyword);
        queryParamMap.put("df", "content_title");
        queryParamMap.put("start", String.valueOf(offset));
        queryParamMap.put("rows", String.valueOf(count));
        queryParamMap.put("hl.simple.pre", hlPre);
        queryParamMap.put("hl.simple.post", hlPos);
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);
        final QueryResponse response = client.query("weibo", queryParams);
        final SolrDocumentList documents = response.getResults();

        for (SolrDocument document : documents) {
            Question q = new Question();
            int id = Integer.parseInt((String) document.getFieldValue("id"));
            String title = (String) document.getFirstValue("title");
            String content = (String) document.getFirstValue("content");
            q.setId(id);
            q.setTitle(title);
            q.setContent(content);
            questionList.add(q);
        }
        return questionList;
    }

    public boolean indexQuestion(int qid, String title, String content) throws Exception {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", qid);
        doc.addField(QUESTION_TITLE_FIELD, title);
        doc.addField(QUESTION_CONTENT_FIELD, content);
        UpdateResponse response = client.add("weibo", doc);
        client.commit("weibo");
        return response != null && response.getStatus() == 0;
    }
}
