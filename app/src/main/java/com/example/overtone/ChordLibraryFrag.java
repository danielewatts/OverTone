package com.example.overtone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.overtone.data.ChordGroup;
import com.example.overtone.data.DifficultyLevel;
import com.example.overtone.data.JsonDataRetrieval;
import com.example.overtone.data.MusicItem;
import com.example.overtone.data.SingularMusicItemDm;
import com.example.overtone.recyclerview.ChordLibRecyclerViewAdapter;
import com.example.overtone.recyclerview.RecyclerViewClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChordLibraryFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChordLibraryFrag extends Fragment implements RecyclerViewClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**Custom input recyclerView class*/
    private RecyclerView recyclerView;
    private ArrayList<MusicItem> musicItemDataModels;
    Gson gson;

    public ChordLibraryFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChordLibraryFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ChordLibraryFrag newInstance(String param1, String param2) {
        ChordLibraryFrag fragment = new ChordLibraryFrag();
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
        return inflater.inflate(R.layout.fragment_chord_library, container, false);
    }

    private static final int VERTICAL_ITEM_SPACE = 1;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.chord_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
//        setData();

        //add ItemDecoration
//        recyclerView.addItemDecoration(new VerticalSpacingItemDecoration(VERTICAL_ITEM_SPACE));
        createData();
        final int VERTICAL = 1;
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        setDatatoRecycler();

    }



    public void createData(){
        ArrayList<SingularMusicItemDm> allChords = getAllChords();
        Map<DifficultyLevel,ChordGroup> difficultyChordGroups = getDifficultyChordGroups();
        final String[] groupNames = {"Bar","Open","Popular"};
        Map<String,ChordGroup> otherGroupings = createOtherChordGroups(groupNames);
        FillChordGroups(difficultyChordGroups,otherGroupings,allChords);
        setAndCombineMusicItems(allChords,difficultyChordGroups,otherGroupings);
        attachChordGroupImages();
    }





    public ArrayList<SingularMusicItemDm> getAllChords(){
        String jsonPath = "chord.json";
        String jsonString = JsonDataRetrieval.loadJSONFromAsset(getContext(),jsonPath);
        gson = new Gson();
        ArrayList<SingularMusicItemDm> singularMusicItemDms = gson.fromJson(jsonString, new TypeToken<ArrayList<SingularMusicItemDm>>(){}.getType());
        /** need to abstract / organize out code blow this line */
        for (SingularMusicItemDm c:singularMusicItemDms) {
            c.setTestImageID(R.drawable.spicychile);
        }
        return singularMusicItemDms;
    }
    public void attachChordGroupImages(){
        for (MusicItem cg: this.musicItemDataModels) {
            if(cg instanceof ChordGroup){
                ((ChordGroup) cg).setImageId();
            }
        }
    }

    public Map<DifficultyLevel,ChordGroup> getDifficultyChordGroups(){
        //uses name value of enum to set chordGroup name and at the same time sets the enum field
        Map<DifficultyLevel,ChordGroup> difficChordGroups = new TreeMap<>();
        for(DifficultyLevel d : DifficultyLevel.values()){
            String nameAndDiffLevel = d.getStrName();
            ChordGroup cg = new ChordGroup(nameAndDiffLevel);
            cg.setChrdGroupDiffLvl(d);
            difficChordGroups.put(d,cg);
        }
        return difficChordGroups;
    }

    public Map<String,ChordGroup> createOtherChordGroups(String[] groupNames){
        Map<String,ChordGroup> otherGroups = new TreeMap<>();
        //creates all other chordObjects
        for (String name: groupNames){
            ChordGroup cg = new ChordGroup(name);
            otherGroups.put(cg.getChordGroupName(),cg);
        }
        return otherGroups;
    }
    public void FillChordGroups(Map<DifficultyLevel,ChordGroup> diffLevelGroups,Map<String,ChordGroup> otherGroups,ArrayList<SingularMusicItemDm> allChords){
        String[] groupNames = new String[otherGroups.keySet().size()];
        groupNames = otherGroups.keySet().toArray(groupNames);
        //goes through whole individual chordList
        for(SingularMusicItemDm chord : allChords){
            //adds to difficulty chordGroups
            ChordGroup cGroup = diffLevelGroups.get(chord.getDiffLevel());
            cGroup.addToChordList(chord);
            /* checking individual chord for group belongings, placing in correct group by
              finding group from map
             */
            if(chord.isBarChord()){
                otherGroups.get(groupNames[0]).addToChordList(chord);
            }
            if(chord.isOpenChord()){
                otherGroups.get(groupNames[1]).addToChordList(chord);
            }
            if(chord.isPopularChord()){
                otherGroups.get(groupNames[2]).addToChordList(chord);
            }
        }

        otherGroups.get(groupNames[0]).setChrdGroupDiffLvl(DifficultyLevel.Hard);
        otherGroups.get(groupNames[1]).setChrdGroupDiffLvl(DifficultyLevel.Easy);
        otherGroups.get(groupNames[2]).setChrdGroupDiffLvl(DifficultyLevel.Medium);

        //All groups now sorted and should contain their necessary individual chords
    }

    public void setAndCombineMusicItems(ArrayList<SingularMusicItemDm> allChords, Map<DifficultyLevel,ChordGroup> difLevels, Map<String,ChordGroup> otherGroups){
        //combine groups held in difLevels map with otherGroupsMap and all singular Chords
        // set those combined objects grouped by their common interface to the class list of music Items
        //desired display order is Diflevel groups, others, all chords
        this.musicItemDataModels = new ArrayList<>();
        this.musicItemDataModels.addAll(difLevels.values());
        this.musicItemDataModels.addAll(otherGroups.values());
        /** NOT adding all chords because dont want these chords to be passed to recycler view*/
//        this.musicItemDataModels.addAll(allChords);

        ///all items are set into the class variable and now ready to be set and passed to the
        // recycler attached to this fragment

    }








    public void setDatatoRecycler(){
        ChordLibRecyclerViewAdapter cvLibRecyclerAdapter = new ChordLibRecyclerViewAdapter(this.musicItemDataModels,this);
        recyclerView.setAdapter(cvLibRecyclerAdapter);
//        DividerItemDecoration
    }



    @Override
    public void onRcViewClick(int position) {
        Toast.makeText(getContext(),"I've been clicked", Toast.LENGTH_SHORT).show();
    }
}