package com.forty7.slidingmenu;

/**
 * 2019.1.4 Forty'7
 * xiaowangboke@vip.qq.com
 */
public class MyBean {
    private String title;
    private boolean isCollection;

    public MyBean(){

    }
    public MyBean(String title){
        this.title = title;
    }
    public MyBean(String title, boolean isCollection){
        this.title = title;
        this.isCollection = isCollection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }
}
