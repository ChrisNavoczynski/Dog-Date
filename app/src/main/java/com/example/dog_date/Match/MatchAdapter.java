package com.example.dog_date.Match;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_date.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchViewHolders> {

    private List<MatchObject> matchesList;
    private Context context;


    public MatchAdapter(List<MatchObject> matchesList, Context context){
        this.matchesList = matchesList;
        this.context = context;
    }

    @Override
    public MatchViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchViewHolders rcv = new MatchViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(MatchViewHolders holder, int position) {
        holder.mMatchId.setText(matchesList.get(position).getUserId());
        holder.mMatchName.setText(matchesList.get(position).getName());
        Picasso.get().load(matchesList.get(position).getImageUrl()).into(holder.mMatchImage);
    }

    @Override
    public int getItemCount() {
        return this.matchesList.size();
    }
}
