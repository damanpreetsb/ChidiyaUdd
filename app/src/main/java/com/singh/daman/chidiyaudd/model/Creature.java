package com.singh.daman.chidiyaudd.model;

/**
 * Created by Daman on 7/5/2017.
 */

public class Creature {
    private String name;
    private int id;
    private String image;
    private int flag;


    public Creature() {
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
