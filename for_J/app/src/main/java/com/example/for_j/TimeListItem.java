package com.example.for_j;

public class TimeListItem {
    private String listId;
    private String listName;
    private String listToday;
    private String listCName;
    private String listColor;
//    private int listStartTime;
//    private int listEndTime;

//    TimeListItem(String listName) {
//        this.listName = listName;
//    }

    TimeListItem(String listId, String listName, String listToday, String listCName, String listColor) {
        this.listId = listId;
        this.listName = listName;
        this.listToday = listToday;
        this.listCName = listCName;
        this.listColor = listColor;
        //this.listStartTime = listStartTime;
        //this.listEndTime = listEndTime;
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

//    public int getListStartTime() {
//        return listStartTime;
//    }
//
//    public int getListEndTime() {
//        return listEndTime;
//    }

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

//    public void setListStartTime(int listStartTime) {
//        this.listStartTime = listStartTime;
//    }
//
//    public void setListEndTime(int listEndTime) {
//        this.listEndTime = listEndTime;
//    }


}
