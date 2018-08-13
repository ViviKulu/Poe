package com.example.vivianbabiryekulumba.poe.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MyWork {

    String exercise;

    public MyWork(String exercise) {
        this.exercise = exercise;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
