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

        adapter = new CustomListAdapter(data, listView.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DataClickedListener());
    }

    private class DataClickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CustomListDataItem dataItem = (CustomListDataItem) parent.getItemAtPosition(position);

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
