package com.example.overtone.recyclerview;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.overtone.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>{
    private ArrayList<Integer> chordDiagramIds;

    public ViewPagerAdapter(ArrayList<Integer> chordDiagramIds) {
        this.chordDiagramIds = chordDiagramIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer currentImageID = chordDiagramIds.get(position);
        holder.chordDiagr.setImageResource(currentImageID);
    }


    @Override
    public int getItemCount() {
        return chordDiagramIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //creates a container/holder to hold the view that needs be attached to the recycler view
        /**Look at res file and compare to objects so all info can be displayed as needed*/
        ImageView chordDiagr;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chordDiagr= itemView.findViewById(R.id.chordDiagramPic);

        }

    }



}

