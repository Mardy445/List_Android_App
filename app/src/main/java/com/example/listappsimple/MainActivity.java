package com.example.listappsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> selectedData = new ArrayList<>();
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.main_list_view);
        listView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

        data.add("1");
        data.add("2");
        data.add("3");

        adapter = new CustomListAdapter(data,selectedData, listView.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DataClickedListener());
    }

    private class DataClickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String data = (String) parent.getItemAtPosition(position);

            if(selectedData.contains(data)) {
                selectedData.remove(data);
                adapter.notifyDataSetChanged();
            }
            else {
                selectedData.add(data);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
