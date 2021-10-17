package com.example.tinyurl.ctrl.data;

/**
 * @author jinlian
 */
public class TinyUrlPair {

    private String tinyUrl;
    private String longUrl;

    public TinyUrlPair(String tinyUrl, String longUrl) {
        this.tinyUrl = tinyUrl;
        this.longUrl = longUrl;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }
}
