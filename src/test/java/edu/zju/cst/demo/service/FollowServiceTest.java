package edu.zju.cst.demo.service;

import edu.zju.cst.demo.DemoApplication;
import edu.zju.cst.demo.model.EntityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class FollowServiceTest {

    @Autowired
    FollowService followService;

    @Test
    public void testFollowService() {
        for (int i = 2; i <= 50; i++) {
            followService.follow(i, EntityType.ENTITY_USER, 1);
        }
    }

    @Test
    public void testFollow() {
        followService.follow(52, EntityType.ENTITY_USER, 51);
    }

    @Test
    public void testUnfollow() {
        followService.unfollow(52, EntityType.ENTITY_USER, 51);
    }
}
