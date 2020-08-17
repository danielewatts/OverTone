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
import com.example.overtone.data.ChordGroup;
import com.example.overtone.data.MusicItem;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;


public class ChordLibRecyclerViewAdapter extends RecyclerView.Adapter<ChordLibRecyclerViewAdapter.ViewHolder> {
    ///RecyclerView is an abstract class so need to implement methods
    private static final String TAG = "HomeRecyclerAdapter";
    private ArrayList<ChordGroup> dataModels;
    private RecyclerViewClickListener rClickListener;


    public ChordLibRecyclerViewAdapter(ArrayList<ChordGroup> dataModelList, RecyclerViewClickListener recyclerViewClickListener) {
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
         ChordGroup cDataModel = dataModels.get(position);
         holder.chordGrouping.setText(cDataModel.getDescription());
         holder.chordIdentities.setText(cDataModel.getName());
         holder.chordImage.setImageResource(cDataModel.getImageID()); // change this to an item property
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //creates a container/holder to hold the view that needs be attached to the recycler view
        /**Look at res file and compare to objects so all info can be displayed as needed*/

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
