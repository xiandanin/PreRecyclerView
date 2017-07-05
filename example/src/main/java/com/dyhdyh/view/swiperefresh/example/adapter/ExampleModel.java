package com.dyhdyh.view.swiperefresh.example.adapter;

/**
 * author  dengyuhan
 * created 2017/7/4 15:31
 */
public class ExampleModel {
    private String title;
    private String image;

    public ExampleModel() {
    }

    public ExampleModel(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
