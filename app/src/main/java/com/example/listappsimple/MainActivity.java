package com.example.listappsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

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

        ListView listView = findViewById(R.id.main_list_view);
        adapter = new CustomListAdapter(data, listView.getContext());
        listView.setAdapter(adapter);
        final EditText input = findViewById(R.id.note_input_edit_text);

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
                    deselectAllItems();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        sliding.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sliding.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
                   data.add(new CustomListDataItem("-"));
                   adapter.notifyDataSetChanged();
                }
                else{
                    sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });

        listView.setOnItemClickListener(new DataClickedListener());
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

    private void deselectAllItems(){
        for(CustomListDataItem dataItem : data){
            dataItem.setSelected(false);
        }
    }


    private class DataClickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CustomListDataItem dataItem = (CustomListDataItem) parent.getItemAtPosition(position);
            if(dataItem.isComplete()){
                return;
            }
            boolean status = !dataItem.isSelected();
            deselectAllItems();
            dataItem.setSelected(status);
            adapter.notifyDataSetChanged();
            setEditItem(status,dataItem);
        }
    }
}
