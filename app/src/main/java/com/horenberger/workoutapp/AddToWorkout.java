package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddToWorkout extends AppCompatActivity {

    //displays existing exercises
    private ListView listview;
    //used for updating the displayed list
    ArrayAdapter<String> adapter;
    //used to pull the existing exercises from storage
    List<String> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_workout);

        listview =  (ListView) findViewById(R.id.exerciselist);
        initializeList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String new_exercise = adapter.getItem(position).replace(' ', '_');
                Intent resultIntent = new Intent();
                resultIntent.putExtra("new_exercise",new_exercise);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    //prepares the list of exercises to be displayed
    void initializeList(){
        //grabbing the files
        File dir = new File(getBaseContext().getFilesDir(), "Exercises");
        files = new ArrayList<String>();
        //putting them into a string array
        String[] cur_files = dir.list();
        System.out.print("Grabbed exercise names\n");
        //replacing spaces with _ and putting the strings into a List (for ease)
        for(int i = 0; i < cur_files.length; i++) {
            cur_files[i] = cur_files[i].replace('_', ' ');
            files.add(cur_files[i]);
            System.out.print(cur_files[i]);
        }

        //telling the adapter to use our List as its content
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, files);
        //connecting the adapter to the display
        listview.setAdapter(adapter);

    }

    //refreshes the List of exercise names and tells the adapter to refresh. Similar to initializeList
    void refreshList() {
        File dir = new File(getBaseContext().getFilesDir(), "Exercises");
        String[] cur_files = dir.list();
        files.clear();
        System.out.print("Grabbed exercise names\n");
        for(int i = 0; i < cur_files.length; i++) {
            cur_files[i] = cur_files[i].replace('_', ' ');
            files.add(cur_files[i]);
            System.out.print(cur_files[i]);
        }
        adapter.notifyDataSetChanged();
    }
}
