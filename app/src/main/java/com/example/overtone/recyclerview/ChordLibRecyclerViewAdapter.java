package com.example.overtone.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.overtone.R;
import com.example.overtone.data.SingularChordDataModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;


public class ChordLibRecyclerViewAdapter extends RecyclerView.Adapter<ChordLibRecyclerViewAdapter.ViewHolder> {
    ///RecyclerView is an abstract class so need to implement methods
    private static final String TAG = "HomeRecyclerAdapter";
    private List<SingularChordDataModel> dataModels;
    private RecyclerViewClickListener rClickListener;


    public ChordLibRecyclerViewAdapter(List<SingularChordDataModel> dataModelList, RecyclerViewClickListener recyclerViewClickListener) {
        this.rClickListener = recyclerViewClickListener;
        this.dataModels = dataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chord_card, parent, false);
        ViewHolder holder = new ViewHolder(view,rClickListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
//        holder.titleText.setText(String.valueOf(dataModels.get(position).getActivityName()));
//        holder.descriptionText.setText(String.valueOf(dataModels.get(position).getDescription()));
//        holder.imageTeaser.setImageResource(R.drawable.guitars);
        SingularChordDataModel cDataModel = dataModels.get(position);
        holder.chordGrouping.setText(String.valueOf(cDataModel.getDifficultyName()));
        holder.chordIdentities.setText(String.valueOf(cDataModel.getChordName()));
        holder.chordImage.setImageResource(R.drawable.spicychile); // change this to an item property
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView chordImage;
        MaterialCardView chordCardPreview;
        TextView chordGrouping;
        TextView chordIdentities;
        RecyclerViewClickListener rycleVclickListener;

        public ViewHolder(@NonNull View itemView,RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            this.chordCardPreview = itemView.findViewById(R.id.chordCardPreview);
            this.chordImage = itemView.findViewById(R.id.imgTeaser);
            this.chordGrouping = itemView.findViewById(R.id.chordGrouping);
            this.chordIdentities = itemView.findViewById(R.id.chordIdentities);
            this.rycleVclickListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            this.rycleVclickListener.onRcViewClick(getAdapterPosition());

        }
    }





}
