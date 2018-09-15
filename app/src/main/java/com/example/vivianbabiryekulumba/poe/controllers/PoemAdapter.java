package com.example.vivianbabiryekulumba.poe.controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vivianbabiryekulumba.poe.R;
import com.example.vivianbabiryekulumba.poe.models.Poem;
import java.util.List;
import static android.content.ContentValues.TAG;

public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.PoemViewHolder> {

    private List<Poem> poemList;

    public PoemAdapter(List<Poem> poemList) {
        this.poemList = poemList;
        Log.d(TAG, "PoemAdapter: size of list here is" + poemList.size());
    }

    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_item_view, parent, false);
        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemViewHolder holder, int position) {
        holder.onBind(poemList.get(position));
        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return poemList.size();
    }


    public class PoemViewHolder extends RecyclerView.ViewHolder {

        TextView poem_title;
        TextView poet_name;

        public PoemViewHolder(View itemView) {
            super(itemView);
            poem_title = itemView.findViewById(R.id.poem_title);
            poet_name = itemView.findViewById(R.id.poet_name);
        }

        public void onBind(Poem poem) {
            poem_title.setText(poem.getTitle());
            poet_name.setText(String.format("By %s", poem.getPoet().getName()));
        }

    }

}
