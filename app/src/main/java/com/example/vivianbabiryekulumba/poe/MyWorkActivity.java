package com.example.vivianbabiryekulumba.poe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vivianbabiryekulumba.poe.models.Exercise;
import com.example.vivianbabiryekulumba.poe.views.ExerciseViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyWorkActivity extends AppCompatActivity {

    private static final String TAG = "MyWorkActivity";
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);
        recyclerView = findViewById(R.id.my_work_recycler);

        final Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("exercise")
                .limitToLast(50);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Exercise exercise = dataSnapshot.getValue(Exercise.class);
                Log.d(TAG, "onChildAdded: " + exercise);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerOptions<Exercise> options =
                new FirebaseRecyclerOptions.Builder<Exercise>()
                        .setQuery(query, Exercise.class)
                        .build();


        recyclerAdapter = new FirebaseRecyclerAdapter<Exercise, ExerciseViewHolder>(options) {

            @NonNull
            @Override
            public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item_view, parent, false);
                return new ExerciseViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position, @NonNull Exercise exercise) {
                holder.setExercise(exercise);
            }
        };

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

}
