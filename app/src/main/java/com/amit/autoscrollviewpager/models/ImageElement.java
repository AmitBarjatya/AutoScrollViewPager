package com.amit.autoscrollviewpager.models;

/**
 * Created by Amit Barjatya on 10/19/17.
 */

public class ImageElement {
    public String imgUrl;
    public int index;

    public ImageElement(int index, String imgUrl) {
        this.imgUrl = imgUrl;
        this.index = index;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
