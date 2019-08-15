package com.example.traceralumni.Model;

public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private boolean isseen;
    private boolean hideforsender;
    private boolean hideforreceiver;

    public Chat(String sender, String receiver, String message, boolean isseen, boolean hideforsender, boolean hideforreceiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isseen = isseen;
        this.hideforsender = hideforsender;
        this.hideforreceiver = hideforreceiver;
    }

    public Chat() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public boolean isHideforsender() {
        return hideforsender;
    }

    public void setHideforsender(boolean hideforsender) {
        this.hideforsender = hideforsender;
    }

    public boolean isHideforreceiver() {
        return hideforreceiver;
    }

    public void setHideforreceiver(boolean hideforreceiver) {
        this.hideforreceiver = hideforreceiver;
    }
}
