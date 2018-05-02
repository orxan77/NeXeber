package com.example.orkhan.nexeber.Models;

/**
 * Created by Orkhan on 3/28/2018.
 */

public class DrawerListItem {

    private String mWebsite;
    private int mServiceIdOfWebsite;

    private boolean isSelected = false;

    public DrawerListItem(String website, int serviceIdOfWebsite) {
        this.mWebsite = website;
        this.mServiceIdOfWebsite = serviceIdOfWebsite;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public int getServiceIdOfWebsite() {
        return mServiceIdOfWebsite;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
