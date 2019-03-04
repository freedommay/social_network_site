package edu.zju.cst.demo.service;

import edu.zju.cst.demo.dao.LoginTicketDAO;
import edu.zju.cst.demo.dao.UserDAO;
import edu.zju.cst.demo.model.LoginTicket;
import edu.zju.cst.demo.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msg", "用户已经存在");
            return map;
        }
        String salt = UUID.randomUUID().toString().substring(0, 5);
        password = DigestUtils.md5Hex(password + salt);
        String headURL = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        Date lastLoginTime = new Date();
        user = new User(username, password, salt, headURL, lastLoginTime);
        userDAO.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }


    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }
        if (!DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
//        user.setLastLoginTime(new Date());
//        userDAO.updateLastUpdateTime(user);
        map.put("ticket", ticket);
        return map;
    }

    private String addLoginTicket(int userID) {
        Date expiredDate = new Date();
        expiredDate.setTime(expiredDate.getTime() + 1000 * 3600 * 24);
        String ticket = UUID.randomUUID().toString().replaceAll("-", "");
        LoginTicket loginTicket = new LoginTicket(userID, ticket, expiredDate, 0);
        loginTicketDAO.addTicket(loginTicket);
        return ticket;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
        LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
        User user = userDAO.selectById(loginTicket.getUserID());
        user.setLastLoginTime(new Date());
        userDAO.updateLastUpdateTime(user);
    }
}
