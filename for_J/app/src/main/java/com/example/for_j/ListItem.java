// half calendar, to-do, habit list뷰에 들어갈 list item관련
package com.example.for_j;

public class ListItem {
    private String listName;
    private String listToday;
    private String listCName;
    private String listColor;

    ListItem(String listName) {
        this.listName = listName;
    }
    ListItem(String listName, String listToday, String listCName, String listColor){
        this.listName = listName;
        this.listToday = listToday;
        this.listCName = listCName;
        this.listColor = listColor;
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




}
