package com.example.demo.model;

public enum Category {
    DIEN_THOAI("Điện thoại"),
    LAPTOP("Laptop");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
