package com.example.listappsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomListDataItem> data = new ArrayList<>();
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.main_list_view);
        listView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

        data.add(new CustomListDataItem("1"));
        data.add(new CustomListDataItem("2"));
        data.add(new CustomListDataItem("3"));
        data.add(new CustomListDataItem("4"));
        data.add(new CustomListDataItem("5"));
        data.add(new CustomListDataItem("6"));
        data.add(new CustomListDataItem("7"));
        data.add(new CustomListDataItem("8"));
        data.add(new CustomListDataItem("9"));
        data.add(new CustomListDataItem("10"));
        data.add(new CustomListDataItem("11"));
        data.add(new CustomListDataItem("12"));
        data.add(new CustomListDataItem("13"));
        data.add(new CustomListDataItem("14"));
        data.add(new CustomListDataItem("15"));
        data.add(new CustomListDataItem("16"));
        data.add(new CustomListDataItem("17"));
        data.add(new CustomListDataItem("18"));
        data.add(new CustomListDataItem("19"));
        data.add(new CustomListDataItem("20"));

        adapter = new CustomListAdapter(data, listView.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DataClickedListener());
    }

    private class DataClickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CustomListDataItem dataItem = (CustomListDataItem) parent.getItemAtPosition(position);
            if(dataItem.isComplete()){
                return;
            }
            boolean status = !dataItem.isSelected();
            unselectAll();
            dataItem.setSelected(status);
            adapter.notifyDataSetChanged();
        }
        
        private void unselectAll(){
            for(CustomListDataItem dataItem : data){
                dataItem.setSelected(false);
            }
        }
    }
}
