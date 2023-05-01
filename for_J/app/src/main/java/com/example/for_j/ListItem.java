// half calendar, to-do, habit list뷰에 들어갈 list item관련
package com.example.for_j;

public class ListItem {
    private String listName;
    private String listToday;
    private String listStartDate;
    private String listEndDate;

    ListItem(String listName) {
        this.listName = listName;
    }
    ListItem(String listName, String listToday){
        this.listName = listName;
        this.listToday = listToday;
    }
    ListItem(String listName, String listToday, String listStartDate, String listEndDate){
        this.listName = listName;
        this.listToday = listToday;
        this.listStartDate = listStartDate;
        this.listEndDate = listEndDate;
    }

    public String getListName() {
        return listName;
    }

    public String getListToday() {
        return listToday;
    }

    public String getListStartDate() {
        return listStartDate;
    }

    public String getListEndDate(){
        return listEndDate;
    }


    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setListToday(String listToday){
        this.listToday = listToday;
    }

    public void setListStartDate(String listStartDate){
        this.listStartDate = listStartDate;
    }

    public void setListEndDate(String listEndDate){
        this.listEndDate = listEndDate;
    }




}
