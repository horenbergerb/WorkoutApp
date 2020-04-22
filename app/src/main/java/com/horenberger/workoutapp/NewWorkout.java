package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NewWorkout extends AppCompatActivity {

    //displays existing exercises
    private ListView listview;
    //used for updating the displayed list
    ArrayAdapter<String>adapter;
    //used to pull the existing exercises from storage
    List<String> files;
    List<Exercise> exercises;

    private Button addexercise;
    private Button removeexercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_workout);

        addexercise = (Button) findViewById(R.id.addexercise);
        removeexercise = (Button) findViewById(R.id.removeexercise);
        listview =  (ListView) findViewById(R.id.exerciselist);
        initializeList();

        addexercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, go to the "AddExercise" menu
                if (v.getId() == R.id.addexercise) {
                    Intent addExercise = new Intent(NewWorkout.this, AddToWorkout.class);
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
        if(resultCode == Activity.RESULT_OK) {
            String[] results = data.getStringExtra("new_exercise").split("/");
            Log.d("TestLog","Test1");
            String new_exercise_name = results[0];
            Log.d("TestLog",new_exercise_name);
            Exercise new_exercise = new Exercise();
            new_exercise.load(NewWorkout.this, new_exercise_name);
            Log.d("TestLog",new_exercise.getName());

            new_exercise.setSets(Integer.parseInt(results[1]));
            new_exercise.setReps(Integer.parseInt(results[2]));
            exercises.add(new_exercise);
            files.add(new_exercise.getName() + " Sets: " + results[1] + " Reps: " + results[2]);
            refreshList();
        }
    }

    //prepares the list of exercises to be displayed and object list
    void initializeList(){
        exercises = new ArrayList<Exercise>();
        files = new ArrayList<String>();

        //telling the adapter to use our List as its content
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, files);
        //connecting the adapter to the display
        listview.setAdapter(adapter);

    }

    //refreshes the List of exercise names and tells the adapter to refresh. Similar to initializeList
    void refreshList() {
        adapter.notifyDataSetChanged();
    }
}

