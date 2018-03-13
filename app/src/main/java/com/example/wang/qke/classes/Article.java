package com.example.wang.qke.classes;

/**
 * Created by wang on 2017/8/3.
 */

public class Article {

    private String id;
    private String title;
    private String author;
    private String pageView;
    private String summary;
    private String pic;
    private String time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }



    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPic() {
        return pic;
    }
    public String getPicUrl() {
        return "http://123.207.61.165/uploads/article/"+pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPageView() {
        return pageView;
    }

    public void setPageView(String pageView) {
        this.pageView = pageView;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
