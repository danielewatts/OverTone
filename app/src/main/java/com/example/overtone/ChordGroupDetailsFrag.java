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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChordGroupDetailsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChordGroupDetailsFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ChordGroup> chordGroups;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChordGroupDetailsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentGroupDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ChordGroupDetailsFrag newInstance(String param1, String param2) {
        ChordGroupDetailsFrag fragment = new ChordGroupDetailsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        TextView tView = view.findViewById(R.id.centerTextWords);
        tView.setText(chordGroups.get(0).getGroupMakeUp());


    }

    public void retrievePassedFragmentData(){
        if(getArguments()!=null){
            ChordGroupDetailsFragArgs args = ChordGroupDetailsFragArgs.fromBundle(getArguments());
//            String templateCode = ChordGroupDetailsFragArgs.fromBundle(getArguments());
            chordGroups = new ArrayList<>(Arrays.asList(args.getChordGroupObjs()));
        }
    }

}
