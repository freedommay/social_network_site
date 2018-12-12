package edu.zju.cst.demo.model;

import java.util.Date;

public class Comment {
    private int id;
    private String content;
    private int UserID;
    private int entityID; // questionID/commentID
    private int entityType;  // question/comment
    private Date createdDate;
    private int status;  // 状态1为删除

    public Comment() { }

    public Comment(String content, int userID, int entityID, int entityType, Date createdDate, int status) {
        this.content = content;
        this.UserID = userID;
        this.entityID = entityID;
        this.entityType = entityType;
        this.createdDate = createdDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String context) {
        this.content = context;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
