package com.layermark.layermark_sarismet.model;

public class CustomToken {
    private long timestamp;
    private String username;

    public CustomToken(long timestamp, String username) {
        this.timestamp = timestamp;
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }
}
