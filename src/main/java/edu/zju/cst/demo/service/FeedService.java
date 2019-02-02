package edu.zju.cst.demo.service;

import edu.zju.cst.demo.dao.FeedDAO;
import edu.zju.cst.demo.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;

    public List<Feed> getUserFeeds(int maxID, List<Integer> userIDs, int count) {
        return feedDAO.selectUserFeeds(maxID, userIDs, count);
    }

    public boolean addFeed(Feed feed) {
        feedDAO.addFeed(feed);
        return feed.getId() > 0;
    }

    public Feed getById(int id) {
        return feedDAO.getFeedById(id);
    }
}
