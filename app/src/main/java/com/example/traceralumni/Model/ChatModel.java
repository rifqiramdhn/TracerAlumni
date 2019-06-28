package com.example.traceralumni.Model;

import java.util.Date;

public class ChatModel {
    private String nama, chat;
    private Date waktu;
    private int fotoResId;

    public ChatModel(String nama, String chat) {
        this.nama = nama;
        this.chat = chat;
    }

    public ChatModel(String nama, String chat, Date waktu, int fotoResId) {
        this.nama = nama;
        this.chat = chat;
        this.waktu = waktu;
        this.fotoResId = fotoResId;
    }

    public String getNama() {
        return nama;
    }

    public String getChat() {
        return chat;
    }

    public Date getWaktu() {
        return waktu;
    }

    public int getFotoResId() {
        return fotoResId;
    }
}
