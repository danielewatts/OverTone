package com.example.overtone.recyclerview;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.overtone.R;
import com.example.overtone.datamodels.chordDataModel;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.time.temporal.TemporalQueries;
import java.util.List;


public class chordLibRecyclerViewAdapter extends RecyclerView.Adapter<chordLibRecyclerViewAdapter.ViewHolder> {
    ///RecyclerView is an abstract class so need to implement methods
    private static final String TAG = "HomeRecyclerAdapter";
    private List<chordDataModel> dataModels;
    private RecyclerViewClickListener rClickListener;


    public chordLibRecyclerViewAdapter (List<chordDataModel> dataModelList, RecyclerViewClickListener recyclerViewClickListener) {
        this.rClickListener = recyclerViewClickListener;
        this.dataModels = dataModelList;
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

        }
    }


    @NonNull
    @Override
    public chordLibRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull chordLibRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
