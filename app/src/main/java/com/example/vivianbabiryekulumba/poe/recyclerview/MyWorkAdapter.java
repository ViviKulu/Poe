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

public class MyWorkAdapter extends RecyclerView.Adapter<MyWorkAdapter.MyWorkViewHolder> {

    private List<MyWork> myWorkList;
    private static final String TAG = "MyWorkAdapter";

    public MyWorkAdapter(List<MyWork> myWorkList) {
        this.myWorkList = myWorkList;
    }

    @NonNull
    @Override
    public MyWorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_work_item_view, parent, false);
        return new MyWorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWorkViewHolder holder, int position) {
        holder.onBind(myWorkList.get(position));
        Log.d(TAG, "onBindViewHolder: " + myWorkList.size());
    }

    @Override
    public int getItemCount() {
        return myWorkList.size();
    }

    public class MyWorkViewHolder extends RecyclerView.ViewHolder {
        TextView my_work_tv;

        public MyWorkViewHolder(View itemView) {
            super(itemView);
            my_work_tv = itemView.findViewById(R.id.my_work_text_view);
        }

        public void onBind(MyWork myWork) {
            my_work_tv.setText(myWork.getExercise());
        }
    }
}
