package com.example.vivianbabiryekulumba.poe.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Exercise {
    private String exercise_content;

    public Exercise() {
    }

    public Exercise(String exercise_content) {
        this.exercise_content = exercise_content;
    }

    public String getExercise_content() {
        return exercise_content;
    }

    public void setExercise_content(String exercise_content) {
        this.exercise_content = exercise_content;
    }
}
