package com.horenberger.workoutapp;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Exercise implements java.io.Serializable{
    //exercise name
    String name;

    //maximum weight ever
    int max_weight;

    //reps and sets
    int sets = 0;
    int reps = 0;

    //is exercise completed
    boolean done = false;

    //constructor
    public Exercise(String name) {
        this.name = name;
    }

    //a billion getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    //incrementers for reps and sets
    public void incReps(){
        this.reps += 1;
    }
    public void decReps(){
        if(this.reps > 0)
            this.reps -= 1;
    }

    public void incSets(){
        this.sets += 1;
    }
    public void decSets(){
        if(this.sets > 0)
            this.sets -= 1;
    }

    public int save(Context context){
        try {
            if (name.matches("[a-zA-Z ]+")) {
                File dir = new File(context.getFilesDir(), "Exercises");
                dir.mkdirs();
                FileOutputStream fos = new FileOutputStream(new File(dir, name.replace(' ', '_')));
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(this);
                os.close();
                fos.close();
            }
            else
                return -1;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return -2;
        } catch (IOException e){
            e.printStackTrace();
            return -2;
        }
        return 1;
    }
}
