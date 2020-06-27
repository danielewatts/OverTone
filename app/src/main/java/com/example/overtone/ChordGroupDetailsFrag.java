package com.example.overtone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.overtone.data.ChordGroup;
import com.example.overtone.data.MusicItem;

import java.util.ArrayList;
import java.util.Arrays;

public class ChordGroupDetailsFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ChordGroup> chordGroups;
    private ChordGroup sentChordGroup;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChordGroupDetailsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrievePassedFragmentData();
//        TextView tView = view.findViewById(R.id.centerTextWords);
//        tView.setText(sentChordGroup.getDescription() + ", " + sentChordGroup.getGroupMakeUp());


    }

    public void retrievePassedFragmentData(){
        if(getArguments()!=null){
            ChordGroupDetailsFragArgs args = ChordGroupDetailsFragArgs.fromBundle(getArguments());
            //receiving chord group data object that was clicked on
              this.sentChordGroup = args.getChordGroupClickedOn();
        }
    }

}
