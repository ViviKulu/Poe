package com.example.vivianbabiryekulumba.poe.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.vivianbabiryekulumba.poe.R;
import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {

    private List<Poem> poemList;
    private static final String TAG = "ThemeAdapter";

    public ThemeAdapter(List<Poem> poemList) {
        this.poemList = poemList;
    }

    @NonNull
    @Override
    public ThemeAdapter.ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_item_view, parent, false);
        return new ThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeAdapter.ThemeViewHolder holder, int position) {
        holder.onBind(poemList.get(position));
        Log.d(TAG, "onBindViewHolder: " + poemList.size());
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder {
        TextView theme;
        EditText practice;

        public ThemeViewHolder(View itemView) {
            super(itemView);
            theme = itemView.findViewById(R.id.theme_tv);
            practice = itemView.findViewById(R.id.theme_practice_et);
        }

        public void onBind(Poem poem) {
            theme.setText(poem.getTitle());
            Log.d(TAG, "onBind: " + poem.getTitle());
        }
    }
}
