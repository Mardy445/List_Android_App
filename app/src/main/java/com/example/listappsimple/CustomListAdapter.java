package com.example.listappsimple;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListAdapter extends BaseAdapter implements ListAdapter {

    private Context mContext;
    private ArrayList<CustomListDataItem> listData;

    public CustomListAdapter(ArrayList<CustomListDataItem> listData, Context context) {
        mContext = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CustomListDataItem data = (CustomListDataItem) getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        TextView view;

        if(!data.isComplete()){
            convertView = inflater.inflate(R.layout.custom_list_item,parent,false);
            view = convertView.findViewById(R.id.data_dump_note_item);
            uncompleteNoteFunctionality(convertView,view,data);
        }
        else{
            convertView = inflater.inflate(R.layout.custom_list_item_hidden,parent,false);
            view = convertView.findViewById(R.id.data_dump_complete_note_item);
            completedNoteFunctionality(convertView,view,data);
            view.setBackgroundColor(convertView.getResources().getColor(R.color.colorCompleteTextBackground));
            view.setTextColor(convertView.getResources().getColor(R.color.colorCompleteText));
            view.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if(data.isSelected()){
            view.setBackgroundColor(convertView.getResources().getColor(R.color.colorSelectedTextBackground));
            view.setTextColor(convertView.getResources().getColor(R.color.colorSelectedText));
        }
        return convertView;
    }

    private void uncompleteNoteFunctionality(View convertView, TextView view, final CustomListDataItem data){
        final Button removeButton = convertView.findViewById(R.id.remove_button);
        view.setText(data.getData());

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setComplete(true);
                data.setSelected(false);
                notifyDataSetChanged();
            }
        });
    }

    private void completedNoteFunctionality(View convertView, TextView view, final CustomListDataItem data){
        final Button undoButton = convertView.findViewById(R.id.undo_button);
        final Button deleteButton = convertView.findViewById(R.id.delete_button);
        view.setText(data.getData());

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setComplete(false);
                notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.remove(data);
                notifyDataSetChanged();
            }
        });
    }


}
