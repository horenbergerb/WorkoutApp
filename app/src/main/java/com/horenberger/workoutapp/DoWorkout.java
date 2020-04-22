package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DoWorkout extends AppCompatActivity {

    private Workout curWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_workout);

        //The workout object with all the exercises you just selected and the desired reps/sets
        curWorkout = (Workout) getIntent().getSerializableExtra("workout");

    }
}
