package com.example.ahmed.movieapp;

/**
 * Created by ahmed on 18/04/16.
 */
public class Reviews {

    private String auther;
    private String content;
    private String url;

    public Reviews() {
    }

    public Reviews(String auther, String content, String url) {
        this.auther = auther;
        this.content = content;
        this.url = url;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
