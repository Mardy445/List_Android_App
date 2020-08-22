package com.example.listappsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomListDataItem> data = new ArrayList<>();
    BaseAdapter adapter;

    CustomListDataItem currentItemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTestData();
        final EditText input = findViewById(R.id.note_input_edit_text);
//        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
//                String text = input.getText().toString();
//                if(isInputDupe(text) || text.equals("")){
//                    return true;
//                }
//
//                if(currentItemToEdit == null){
//                    data.add(new CustomListDataItem(text));
//                }
//                else{
//                    currentItemToEdit.setData(text);
//                    currentItemToEdit.setSelected(false);
//                }
//                adapter.notifyDataSetChanged();
//                input.getText().clear();
//                return true;
//            }
//        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(currentItemToEdit != null){
                    currentItemToEdit.setData(s.toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        final SlidingUpPanelLayout sliding = findViewById(R.id.sliding_layout);
        sliding.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
//        sliding.getChildAt(1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(sliding.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
//                   data.add(new CustomListDataItem(""));
//                   adapter.notifyDataSetChanged();
//                }
//            }
//        });



        ListView listView = findViewById(R.id.main_list_view);
        listView.setOnItemClickListener(new DataClickedListener());
    }

    private boolean isInputDupe(String input){
        for(CustomListDataItem item : data){
            if(item.getData().equals(input)){
                return true;
            }
        }
        return false;
    }

    protected void setEditItem(boolean isAnythingSelected, CustomListDataItem item){

        final SlidingUpPanelLayout sliding = findViewById(R.id.sliding_layout);
        sliding.setPanelState(isAnythingSelected ? SlidingUpPanelLayout.PanelState.EXPANDED : SlidingUpPanelLayout.PanelState.COLLAPSED);
        currentItemToEdit = isAnythingSelected ? item : null;

        if(!isAnythingSelected){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        final EditText input = findViewById(R.id.note_input_edit_text);
        input.setText(isAnythingSelected ? item.getData() : "");
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
            setEditItem(status,dataItem);
        }

        private void unselectAll(){
            for(CustomListDataItem dataItem : data){
                dataItem.setSelected(false);
            }
        }
    }
}
