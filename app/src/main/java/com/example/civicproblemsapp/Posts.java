package com.example.civicproblemsapp;

public class Posts {

    private String id;
    private String date;
    private String img_url;
    private int upVoteCount;
    private String status;

    public Posts(String id, String date, String img_url, int upVoteCount, String status, String messageBoard) {
        this.id = id;
        this.date = date;
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

    private String messageBoard;


}
