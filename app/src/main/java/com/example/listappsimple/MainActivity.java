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
import android.widget.ImageView;
import android.widget.ListView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //This list represents the note data on the application
    ArrayList<CustomListDataItem> data;

    //This object is the adapter for the ListView
    BaseAdapter adapter;

    //This object represents the current item to edit, IE the currently selected item
    CustomListDataItem currentItemToEdit = null;

    //These objects are used for saving data to file
    FileOutputStream fos;
    ObjectOutputStream oos;

    @Override
    /*
    This method is called upon creation of the main activity.
    It will set up the ListView and its adapter.
    It will also set up all the various listeners for the main activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadStateOfApplication();

        ListView listView = findViewById(R.id.main_list_view);
        adapter = new CustomListAdapter(data, listView.getContext());
        listView.setAdapter(adapter);

        final EditText input = findViewById(R.id.note_input_edit_text);
        input.addTextChangedListener(new ListenerOnEditTextChanged());

        final SlidingUpPanelLayout sliding = findViewById(R.id.sliding_layout);
        sliding.addPanelSlideListener(new ListenerSlidingPanel());

        sliding.getChildAt(1).setOnClickListener(new ListenerOnSlideUpPanelClicked());

        listView.setOnItemClickListener(new ListenerDataClicked());
    }

    /*
    This method will set the state of the panel, soft keyboard and edit text input
    depending on whether anything is selected or not.
     */
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

    /*
    This will deselect all items.
     */
    private void deselectAllItems(){
        for(CustomListDataItem dataItem : data){
            dataItem.setSelected(false);
        }
    }

    @Override
    /*
    This method is called when any button is pressed on the mobile device.
    I use this method to change the functionality of the "back" button if the sliding panel is open.
     */
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final SlidingUpPanelLayout sliding = findViewById(R.id.sliding_layout);
            if(sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
                sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause(){
        super.onPause();
        saveStateOfApplication();
    }

    public void saveStateOfApplication(){
        deselectAllItems();
        try{
            FileOutputStream fos = openFileOutput("saved.ser",MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        }
        catch (IOException e){}
    }

    public void loadStateOfApplication(){
        try{
            FileInputStream fis = openFileInput("saved.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (ArrayList<CustomListDataItem>) ois.readObject();
            fis.close();
            ois.close();
        }
        catch (FileNotFoundException e){
            data = new ArrayList<>();
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //LISTENERS for various parts of the main activity


    /*
    If a ListView item is clicked, set that item to be "selected" and notify the adapter.
     */
    private class ListenerDataClicked implements AdapterView.OnItemClickListener {
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

    /*
    If the edit text box text is changed, change the data of the currently selected item
     */
    private class ListenerOnEditTextChanged implements TextWatcher {
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
    }

    /*
    If the state of the sliding panel is changed,
    alter the selected item, soft keyboard, and sliding panel icon accordingly.
     */
    private class ListenerSlidingPanel implements SlidingUpPanelLayout.PanelSlideListener {
        @Override
        public void onPanelSlide(View panel, float slideOffset) {

        }

        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            ImageView image = findViewById(R.id.sliding_panel_icon);
            if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                image.setImageResource(R.drawable.add_icon);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                deselectAllItems();
                adapter.notifyDataSetChanged();
            }
            else{
                image.setImageResource(R.drawable.hide_icon);
            }
        }
    }

    /*
    If the panel is clicked and the panel is collapsed, instead of opening the panel, make a new data item.
    Otherwise, close the panel.
     */
    private class ListenerOnSlideUpPanelClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final SlidingUpPanelLayout sliding = findViewById(R.id.sliding_layout);
            if(sliding.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
                data.add(new CustomListDataItem(""));
                adapter.notifyDataSetChanged();
            }
            else{
                sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        }
    }
}
