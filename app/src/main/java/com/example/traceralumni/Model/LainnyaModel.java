package com.example.traceralumni.Model;

public class LainnyaModel {
    private String item;
    private int iconResId;

    public LainnyaModel(String item, int iconResId) {
        this.item = item;
        this.iconResId = iconResId;
    }

    public String getItem() {
        return item;
    }

    public int getIconResId() {
        return iconResId;
    }
}
