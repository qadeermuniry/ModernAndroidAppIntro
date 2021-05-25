package com.netlogiqs.muniryonboarding;


public class OnBoardingModel {
    String title, desc;
    int drawable;

    public OnBoardingModel(String title, String desc, int drawable) {
        this.title = title;
        this.desc = desc;
        this.drawable = drawable;
    }


    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getDrawable() {
        return drawable;
    }
}