package com.example.traceralumni.Model;

public class UserModel {
    private String id;
    private String nim;
    private String username;
    private String imageUrl;

    public UserModel() {

    }

    public UserModel(String id, String nim, String username, String imageUrl) {
        this.id = id;
        this.nim = nim;
        this.username = username;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
