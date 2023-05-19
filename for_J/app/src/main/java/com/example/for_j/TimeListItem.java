package com.example.for_j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeListItem {
    private String listId;
    private String listName;
    private String listToday;
    private String listCName;
    private String listColor;
/*    private int[] listOrder;
    private String[] listStartTime;
    private String[] listEndTime;
    private String[] listTimeTaken;*/

    private int numOfOrder;
    private List<Integer> listOrder = null;
    private List<String> listStartTime = null;
    private List<String> listEndTime = null;
    private List<String> listTimeTaken = null;
    private String totalTimeTaken = null;

    TimeListItem(String listName) {
        this.listName = listName;
    }

    TimeListItem(String listId, String listName, String listToday, String listCName, String listColor){
        this.listId = listId;
        this.listName = listName;
        this.listToday = listToday;
        this.listCName = listCName;
        this.listColor = listColor;
        this.numOfOrder = 0;
        this.listOrder = new ArrayList<>();
        this.listStartTime = new ArrayList<>();
        this.listEndTime = new ArrayList<>();
        this.listTimeTaken = new ArrayList<>();

    }

    public void addListOrder (int listOrder){
        this.listOrder.add(listOrder);
    }

    public void addListStartTime (String listStartTime){
        this.listStartTime.add(listStartTime);
    }

    public void addListEndTime (String listEndTime){
        this.listEndTime.add(listEndTime);
    }

    public void addListTimeTaken (String listTimeTaken){
        this.listTimeTaken.add(listTimeTaken);
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

    public int getNumOfOrder() {return numOfOrder;};

    public List<String> getListStartTime() {
        return listStartTime;
    }

    public List<String>getListEndTime() {
        return listEndTime;
    }

    public List<String> getListTimeTaken() {
        return listTimeTaken;
    }

    public List<Integer> getListOrder() {
        return listOrder;
    }

    public String getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public int getLastOrder() {
        int last = listOrder.size() - 1;
        if (last < 0){
            return 0;
        }{
            return listOrder.get(last);
        }
    }

    public void setNumOfOrder(int numOfOrder) {
        this.numOfOrder = numOfOrder;
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

    public void setListStartTime(List<String> listStartTime) {
        this.listStartTime = listStartTime;
    }

    public void setListEndTime(List<String> listEndTime) {
        this.listEndTime = listEndTime;
    }

    public void setListTimeTaken(List<String> listTimeTaken) {
        this.listTimeTaken = listTimeTaken;
    }

    public void setListOrder(List<Integer> Order) {
        this.listOrder = listOrder;
    }

    public void setTotalTimeTaken(String totalTimeTaken){
        this.totalTimeTaken = totalTimeTaken;
    }


}
