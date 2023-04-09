package com.example.for_j.dbSchemaClass;

public class CategorySchemaClass {
    private String loginID = null;
    private String name = null;
    private String color = null;
    private int isTodo;

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIsTodo() {
        return isTodo;
    }

    public void setIsTodo(int isTodo) {
        this.isTodo = isTodo;
    }

}
