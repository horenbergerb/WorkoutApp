package com.horenberger.workoutapp;

import java.io.Serializable;
import java.util.List;

//This is the thing that holds all the data for a workout session
public class Workout implements Serializable {

    //This is the list of exercises. You'll increment the curReps and curSets of these guys
    //And you'll access the reps/sets of them as well
    public List<Exercise> exercises;

    Workout(){}

    Workout(List<Exercise> exercises) {
        this.exercises = exercises;
    }

}
