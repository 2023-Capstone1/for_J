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
    List<Integer> listOrder = null;
    List<String> listStartTime = null;
    List<String> listEndTime = null;
    List<String> listTimeTaken = null;

    TimeListItem(String listName) {
        this.listName = listName;
    }

    TimeListItem(String listId, String listName, String listToday, String listCName, String listColor){
        this.listId = listId;
        this.listName = listName;
        this.listToday = listToday;
        this.listCName = listCName;
        this.listColor = listColor;
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

    public int getLastOrder() {
        int last = listOrder.size() - 1;
        return listOrder.get(last);
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

}
