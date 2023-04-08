// half calendar, to-do, habit list뷰에 들어갈 list item관련
package com.example.for_j;

public class ListItem {

    private String listName;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    ListItem(String listName) {
        this.listName = listName;
    }

}
