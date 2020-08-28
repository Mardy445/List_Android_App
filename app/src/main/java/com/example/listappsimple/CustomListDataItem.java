package com.example.listappsimple;

import java.io.Serializable;

public class CustomListDataItem implements Serializable {

    private String data;
    private boolean selected = false;
    private boolean complete = false;

    public CustomListDataItem(String data){
        this.data = data;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isComplete(){
        return complete;
    }

    public void setComplete(boolean complete){
        this.complete = complete;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }


}
