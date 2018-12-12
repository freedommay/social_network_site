package edu.zju.cst.demo.model;

public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headURL;

    public User() {}

    public User(String name, String password, String salt, String headURL) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.headURL = headURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadURL() {
        return headURL;
    }

    public void setHeadURL(String headURL) {
        this.headURL = headURL;
    }
}
