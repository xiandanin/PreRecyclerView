package com.dyhdyh.view.prerecyclerview.example.adapter;

/**
 * author  dengyuhan
 * created 2017/7/4 15:31
 */
public class ExampleModel {
    private String title;
    private String image;
    private int color;

    public ExampleModel() {
    }

    public ExampleModel(String title, String image, int color) {
        this.title = title;
        this.image = image;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
