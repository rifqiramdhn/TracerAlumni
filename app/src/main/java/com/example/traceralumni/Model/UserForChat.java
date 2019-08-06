package com.example.traceralumni.Model;

public class UserForChat {
    private String id;
    private String username;
    private String imageURL;
    private String nim;

    public UserForChat(){

    }

    public UserForChat(String id, String username, String imageURL, String nim) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.nim = nim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
