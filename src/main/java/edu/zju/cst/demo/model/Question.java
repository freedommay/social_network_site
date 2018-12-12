package edu.zju.cst.demo.model;

import java.util.Date;

public class Question {
    private int id;
    private String title;
    private String content;
    private Date createdDate;
    private int userID;
    private int commentCount;

    public Question() {}

    public Question(String title, String content, Date createdDate, int userID, int commentCount) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.userID = userID;
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
