package com.example.civicproblemsapp;

public class Chats {

    private String board_id;
    private String message;
    private String sender;
    private long time;

    public Chats(String board_id, String message, String sender, long time) {
        this.board_id = board_id;
        this.message = message;
        this.sender = sender;
        this.time = time;
    }

    public String getBoard_id() {
        return board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
