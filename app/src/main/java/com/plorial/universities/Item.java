package com.plorial.universities;

/**
 * Created by plorial on 6/20/16.
 */
public class Item {

    public String name;
    public String city;
    private int tag;

    public Item(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
