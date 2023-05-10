// half calendar, to-do, habit list뷰에 들어갈 list item관련
package com.example.for_j;

public class ListItem {
    private String listId;
    private String listName;
    private String listToday;
    private String listCName;
    private String listColor;
    private String listStartDate;
    private String listEndDate;
    private int listState;

    ListItem(String listName) {
        this.listName = listName;
    }
    ListItem(String listId, String listName, String listToday, String listCName, String listColor, int listState){
        this.listId = listId;
        this.listName = listName;
        this.listToday = listToday;
        this.listCName = listCName;
        this.listColor = listColor;
        this.listState = listState;
    }
    ListItem(String listId, String listName, String listToday, String listColor, String listStartDate, String listEndDate, int listState){
        this.listId = listId;
        this.listName = listName;
        this.listToday = listToday;
        this.listColor = listColor;
        this.listStartDate = listStartDate;
        this.listEndDate = listEndDate;
        this.listState = listState;
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

    public String getListStartDate() {return listStartDate;}

    public String getListEndDate() {return listEndDate;}

    public int getListState() {
        return listState;
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

    public void setListState(int listState) {
        this.listState = listState;
    }

    public void setListStartDate(String listStartDate) {this.listStartDate = listStartDate;}

    public void setListEndDate(String listEndDate) {this.listEndDate = listEndDate;}


}
