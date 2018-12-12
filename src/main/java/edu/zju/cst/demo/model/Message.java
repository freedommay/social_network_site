package edu.zju.cst.demo.model;

import java.util.Date;

public class Message {
    private int id;
    private int fromID;
    private int toID;
    private String content;
    private int hasRead;  // 0 未读 1 已读
    private Date createdDate;
    private String conversationID;

    public Message() { }

    public Message(int fromID, int toID, String content, int hasRead, Date createdDate) {
        this.fromID = fromID;
        this.toID = toID;
        this.content = content;
        this.hasRead = hasRead;
        this.createdDate = createdDate;
    }

    public Message(int fromID, int toID, String content, int hasRead, Date createdDate, String conversationID) {
        this.fromID = fromID;
        this.toID = toID;
        this.content = content;
        this.hasRead = hasRead;
        this.createdDate = createdDate;
        this.conversationID = conversationID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromID() {
        return fromID;
    }

    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    public int getToID() {
        return toID;
    }

    public void setToID(int toID) {
        this.toID = toID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getConversationID() {
        if (fromID <= toID) {
            return String.format("%d_%d", fromID, toID);
        }
        return String.format("%d_%d", toID, fromID);
    }

    public void setConversationID(int fromID, int toID) {
        if (fromID <= toID) {
            this.conversationID = String.format("%d_%d", fromID, toID);
        }
        this.conversationID = String.format("%d_%d", toID, fromID);
    }
}
