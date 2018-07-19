package com.blogspot.tiaotone.simpletodolist;

public class ItemData {
    public String name;
    public String code;

    public ItemData(String name, String code) {
        this.name = name;
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public String getCode() {
        return code;
    }
}
