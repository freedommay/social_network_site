package edu.zju.cst.demo.model;

import java.util.Date;

public class LoginTicket {
    private int id;
    private int userID;
    private String ticket;
    private Date expired; // 0有效，1无效
    private int status;

    public LoginTicket() { }

    public LoginTicket(int userID, String ticket, Date expired, int status) {
        this.userID = userID;
        this.ticket = ticket;
        this.expired = expired;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
