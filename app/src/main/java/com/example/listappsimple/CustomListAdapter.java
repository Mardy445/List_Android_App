package com.example.listappsimple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        convertView = inflater.inflate(R.layout.custom_list_item,parent,false);

        TextView view = convertView.findViewById(R.id.data_dump_note_item);
        Button button = convertView.findViewById(R.id.data_dump_remove_button);
        view.setText(data.getData());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData.remove(data);
                notifyDataSetChanged();
            }
        });

        if(data.isSelected()){
            view.setBackgroundColor(convertView.getResources().getColor(R.color.colorSelectedTextBackground));
            view.setTextColor(convertView.getResources().getColor(R.color.colorSelectedText));
        }
        return convertView;
    }


}
