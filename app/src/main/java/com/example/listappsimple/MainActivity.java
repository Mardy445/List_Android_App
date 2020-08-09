package com.example.listappsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomListDataItem> data = new ArrayList<>();
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTestData();

        final EditText input = findViewById(R.id.note_input_edit_text);
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
                addItemToList(input.getText().toString());
                input.getText().clear();
                //InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });
        //listView.setOnItemClickListener(new DataClickedListener());
    }

    private void addItemToList(String input){
        if(isInputDupe(input) || input.equals("")){
            return;
        }
        data.add(new CustomListDataItem(input));
        adapter.notifyDataSetChanged();
    }

    private boolean isInputDupe(String input){
        for(CustomListDataItem item : data){
            if(item.getData().equals(input)){
                return true;
            }
        }
        return false;
    }

    private void addTestData(){
        ListView listView = findViewById(R.id.main_list_view);

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


        adapter = new CustomListAdapter(data, listView.getContext());
        listView.setAdapter(adapter);
    }

//    private class DataClickedListener implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            CustomListDataItem dataItem = (CustomListDataItem) parent.getItemAtPosition(position);
//            if(dataItem.isComplete()){
//                return;
//            }
//            boolean status = !dataItem.isSelected();
//            unselectAll();
//            dataItem.setSelected(status);
//            adapter.notifyDataSetChanged();
//        }
//
//        private void unselectAll(){
//            for(CustomListDataItem dataItem : data){
//                dataItem.setSelected(false);
//            }
//        }
//    }
}
