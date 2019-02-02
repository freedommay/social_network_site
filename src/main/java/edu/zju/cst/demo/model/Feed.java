package edu.zju.cst.demo.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Feed {
    private int id;
    private int type;
    private int userID;
    private Date createdDate;
    private String data;
    private JSONObject jsonObject = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String get(String key) {
        return jsonObject == null ? null : jsonObject.getString(key);
     }

     public String getData() {
        return data;
     }

    public void setData(String data) {
        this.data = data;
        jsonObject = JSONObject.parseObject(data);
    }
}
