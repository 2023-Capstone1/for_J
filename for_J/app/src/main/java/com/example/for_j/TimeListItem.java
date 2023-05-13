package com.example.for_j;

public class TimeListItem {
    private String listId;
    private String listName;
    private String listToday;
    private String listCName;
    private String listColor;
    private String listStartTime;
    private String listEndTime;
    private String listTimeTaken;
    private int listOrder;

    TimeListItem(String listName) {
        this.listName = listName;
    }

    TimeListItem(String listId, String listName, String listToday, String listCName, String listColor, String listStartTime, String listEndTime, String listTimeTaken, int listOrder) {
        this.listId = listId;
        this.listName = listName;
        this.listToday = listToday;
        this.listCName = listCName;
        this.listColor = listColor;
        this.listStartTime = listStartTime;
        this.listEndTime = listEndTime;
        this.listTimeTaken = listTimeTaken;
        this.listOrder = listOrder;
    }

    public String getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public String getListToday() {
        return listToday;
    }

    public String getListCName() {
        return listCName;
    }

    public String getListColor() {
        return listColor;
    }

    public String getListStartTime() {
        return listStartTime;
    }

    public String getListEndTime() {
        return listEndTime;
    }

    public String getListTimeTaken() {
        return listTimeTaken;
    }

    public int getListOrder() {
        return listOrder;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setListToday(String listToday){
        this.listToday = listToday;
    }

    public void setListCName(String listCName) {
        this.listCName = listCName;
    }

    public void setListColor(String listColor){
        this.listColor = listColor;
    }

    public void setListStartTime(String listStartTime) {
        this.listStartTime = listStartTime;
    }

    public void setListEndTime(String listEndTime) {
        this.listEndTime = listEndTime;
    }

    public void setListTimeTaken(String listTimeTaken) {
        this.listTimeTaken = listTimeTaken;
    }

    public void setListOrder(int Order) {
        this.listOrder = listOrder;
    }

}
