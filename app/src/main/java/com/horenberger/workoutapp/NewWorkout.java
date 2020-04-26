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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NewWorkout extends AppCompatActivity {

    //displays selected exercises and sets/reps
    private ListView listview;
    //used for updating the displayed list
    ArrayAdapter<String>adapter;
    //container for the strings that listview displays
    List<String> selectedexercises = new ArrayList<String>();
    //holds the actual exercises classes we'll put into the workout object
    ArrayList<Exercise> exercises;

    private Button addexercise;
    private Button removeexercise;
    private Button startworkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_workout);

        startworkout = (Button) findViewById(R.id.startworkout);
        addexercise = (Button) findViewById(R.id.addexercise);
        removeexercise = (Button) findViewById(R.id.removeexercise);
        listview =  (ListView) findViewById(R.id.exerciselist);

        //prepping the list to empty
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

        startworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, go to the actual workout screen
                if (v.getId() == R.id.startworkout && exercises.size() > 0) {
                    Intent doWorkout = new Intent(NewWorkout.this, DoWorkout.class);
                    //Creating a workout object and passing to the new activity
                    Workout curWorkout = new Workout(exercises);
                    doWorkout.putExtra("workout", (Serializable) curWorkout);
                    //Starting the new activity
                    startActivity(doWorkout);
                    //finishing the current activity since we won't be coming back here
                    finish();
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

            String new_exercise_name = results[0];
            Exercise new_exercise = new Exercise();
            new_exercise.load(NewWorkout.this, new_exercise_name);

            new_exercise.setSets(Integer.parseInt(results[1]));
            new_exercise.setReps(Integer.parseInt(results[2]));
            exercises.add(new_exercise);
            selectedexercises.add(new_exercise.getName() + " Sets: " + results[1] + " Reps: " + results[2]);
            refreshList();
        }
    }

    //prepares the list of exercises to be displayed and object list
    void initializeList(){
        exercises = new ArrayList<Exercise>();
        selectedexercises = new ArrayList<String>();

        //telling the adapter to use our List as its content
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, selectedexercises);
        //connecting the adapter to the listview display
        listview.setAdapter(adapter);

    }

    //refreshes the List of exercise names and tells the adapter to refresh.
    void refreshList() {
        adapter.notifyDataSetChanged();
    }
}

