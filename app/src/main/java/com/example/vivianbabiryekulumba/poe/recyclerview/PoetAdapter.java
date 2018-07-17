package com.example.vivianbabiryekulumba.poe.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vivianbabiryekulumba.poe.R;
import java.util.List;
import static android.content.ContentValues.TAG;

public class PoetAdapter extends RecyclerView.Adapter<PoetAdapter.PoemViewHolder> {

    private List<Poem> poemList;

    public PoetAdapter(List<Poem> poemList) {
        this.poemList = poemList;
        Log.d(TAG, "PoetAdapter: size of list here is" + poemList.size());
    }

    @NonNull
    @Override
    public PoemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_item_view, parent, false);
        Log.d(TAG, "onCreateViewHolder: size of list here is:" + poemList.size());
        return new PoemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemViewHolder holder, int position) {
        holder.onBind(poemList.get(position));
        Log.d(TAG, "onBindViewHolder: " + poemList);
    }

    @Override
    public int getItemCount() {
        if(poemList != null){
            return poemList.size();
        }else{
            Log.d(TAG, "getItemCount: out of data");
        }
        return 1;
    }


    public class PoemViewHolder extends RecyclerView.ViewHolder {

        TextView poem_title;
        TextView poem_content;
        TextView poem_url;
        TextView poet_name;
        TextView poet_url;

        public PoemViewHolder(View itemView) {
            super(itemView);
            poem_title = itemView.findViewById(R.id.poem_title);
            poem_content = itemView.findViewById(R.id.poem_content);
            poem_url = itemView.findViewById(R.id.poem_url);
            poet_name = itemView.findViewById(R.id.poet_name);
            poet_url = itemView.findViewById(R.id.poet_url);
        }

        public void onBind(Poem poem) {
            poem_title.setText(poem.getTitle());
            poem_content.setText(poem.getContent());
            poem_url.setText(poem.getUrl());
            poet_name.setText(poem.getPoet().getName());
            poet_url.setText(poem.getPoet().getUrl());

        }

    }
}
