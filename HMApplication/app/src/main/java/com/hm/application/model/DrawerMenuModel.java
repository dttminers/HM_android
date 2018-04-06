package com.hm.application.model;

import java.util.ArrayList;

public class DrawerMenuModel {

    String menuHeader;
    ArrayList menuItem;

    public DrawerMenuModel(String headerName, ArrayList itemList) {
        menuHeader = headerName;
        menuItem = itemList;
    }

    public String getMenuHeader() {
        return menuHeader;
    }

    public void setMenuHeader(String menuHeader) {
        this.menuHeader = menuHeader;
    }

    public ArrayList getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(ArrayList menuItem) {
        this.menuItem = menuItem;
    }
}
