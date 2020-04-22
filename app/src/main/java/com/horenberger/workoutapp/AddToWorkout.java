package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;

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

    private Button completeadd;
    private NumberPicker setpicker;
    private NumberPicker reppicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_workout);

        completeadd = (Button) findViewById(R.id.completeadd);
        setpicker = (NumberPicker) findViewById(R.id.setspicker);
        reppicker = (NumberPicker) findViewById(R.id.repspicker);

        setpicker.setMinValue(1);
        setpicker.setMaxValue(20);

        reppicker.setMinValue(1);
        reppicker.setMaxValue(50);

        final String[] cur_exercise = {null};

        listview =  (ListView) findViewById(R.id.exerciselist);
        initializeList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cur_exercise[0] = adapter.getItem(position).replace(' ', '_');
            }
        });

        completeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, go to the "AddExercise" menu
                if (v.getId() == R.id.completeadd && cur_exercise[0] != null) {
                    cur_exercise[0] += '/' + Integer.toString(setpicker.getValue()) + '/' + Integer.toString((reppicker.getValue()));
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("new_exercise",cur_exercise[0]);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
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
