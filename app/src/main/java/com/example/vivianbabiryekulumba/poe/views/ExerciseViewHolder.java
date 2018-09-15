package com.example.vivianbabiryekulumba.poe.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vivianbabiryekulumba.poe.R;
import com.example.vivianbabiryekulumba.poe.models.Exercise;

public class ExerciseViewHolder extends RecyclerView.ViewHolder {

    private TextView exercise_content;

    public ExerciseViewHolder(View itemView) {
        super(itemView);

        exercise_content = itemView.findViewById(R.id.exercise_content);
    }

    public void setExercise(Exercise exercise) {
        exercise_content.setText(exercise.getExercise_content());
    }
}
