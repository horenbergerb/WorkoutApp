package com.horenberger.workoutapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManageExercises extends Activity{

    //displays existing exercises
    private ListView listview;
    //used for updating the displayed list
    ArrayAdapter<String>adapter;
    //used to pull the existing exercises from storage
    List<String> files = new ArrayList<String>();

    private Button addexercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.exercisemanager);

        addexercise = (Button) findViewById(R.id.addexercise);
        listview =  (ListView) findViewById(R.id.exerciselist);
        initializeList();

        addexercise.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, go to the "AddExercise" menu
                if (v.getId() == R.id.addexercise) {
                    Intent addExercise = new Intent(ManageExercises.this, AddExercise.class);
                    //waits for the AddExercise activity to complete, then runs onActivityResult below
                    startActivityForResult(addExercise, 1);
                }
            }
        });
    }

    //refreshes the list being displayed after new items were added
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        refreshList();
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
