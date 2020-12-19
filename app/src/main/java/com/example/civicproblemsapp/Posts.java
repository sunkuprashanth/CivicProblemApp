package com.example.civicproblemsapp;

public class Posts {

    private Object _id;
    private String id;
    private String user;
    private String date;
    private String location;
    private String img_url;
    private int upVoteCount;
    private String status;
    private String messageBoard;

    public Object get_id() {
        return _id;
    }

    public void set_id(Object __id) {
        this._id = __id;
    }

    public Posts(){

    }

    public Posts(String user, String date, String location, String img_url, int upVoteCount, String status, String messageBoard) {
        this.id = "";
        this.user = user;
        this.date = date;
        this.location = location;
        this.img_url = img_url;
        this.upVoteCount = upVoteCount;
        this.status = status;
        this.messageBoard = messageBoard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getUpVoteCount() {
        return upVoteCount;
    }

    public void setUpVoteCount(int upVoteCount) {
        this.upVoteCount = upVoteCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessageBoard() {
        return messageBoard;
    }

    public void setMessageBoard(String messageBoard) {
        this.messageBoard = messageBoard;
    }

}
