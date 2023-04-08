package com.example.for_j.dbSchemaClass;

public class TodoSchemaClass {
    private String loginID;
    private String name;
    private String date;
    private String cName;
    private String cColor;
    private int state = 0;

    // Getter methods
    public String getLoginID() {
        return loginID;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getCName() {
        return cName;
    }

    public String getCColor() {
        return cColor;
    }

    public int getState() {
        return state;
    }

    // Setter methods
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public void setCColor(String cColor) {
        this.cColor = cColor;
    }

    public void setState(int state) {
        this.state = state;
    }
}
