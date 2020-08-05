package com.example.listappsimple;

public class CustomListDataItem{

    private String data;
    private boolean selected = false;

    public CustomListDataItem(String data){
        this.data = data;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public String getData(){
        return data;
    }


}
