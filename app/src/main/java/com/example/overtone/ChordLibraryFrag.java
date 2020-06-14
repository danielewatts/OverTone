package com.example.overtone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    private ArrayList<SingularMusicItemDm> chordDataModelList;
    private ArrayList<ChordGroup> chordGroupList;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.chord_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
//        setData();
        spoolData();
        setDatatoRecycler();





    }


//    public void setData(){
//        chordDataModelList = new ArrayList<>();
//        Image testImage = null; //deal with this
//        ///testing code
//        for(int i = 1; i <=10; i++ ){
//            int difnum = i;
//            if(i >4){
//                difnum = 2;
//            }
//            SingularChordDm cdm = new SingularChordDm("G",difnum,testImage);
//            chordDataModelList.add(cdm);
//        }
//
//    }

    public void spoolData(){
//        String jsonFilePath = "chord.json";
//        String jsonString = JsonDataRetrieval.loadJSONFromAsset(getContext(),jsonFilePath);
//        gson = new Gson();
//        SingularChordDm singChord = gson.fromJson(jsonString,SingularChordDm.class);
//        int j = 5;
//        j = j+2;

    }

    public ArrayList<ChordGroup> createDifficultyChordGroups(DifficultyLevel difficultyLevel){
        //uses name value of enum to set chordGroup name and at the same time sets the enum field
        ArrayList<ChordGroup> allDifficultyChordGroups = new ArrayList<>();
        //creates all chordGroup objs in the DifficultyLevels
        for(DifficultyLevel d : DifficultyLevel.values()){
            String nameAndDiffLevel = d.getStrName();
            ChordGroup cg = new ChordGroup(nameAndDiffLevel);
            cg.setChrdGroupDiffLvl(d);
            allDifficultyChordGroups.add(cg);
        }
        return allDifficultyChordGroups;
    }

    public ArrayList<ChordGroup> createOtherChordGroup(ArrayList<String> groupNames){
        ArrayList<ChordGroup> otherGroups = new ArrayList<>();
        //creates all other chordObjects
        for (String name: groupNames){
            ChordGroup cg = new ChordGroup(name);
            otherGroups.add(cg);
        }
        return otherGroups;
    }
    public ArrayList<ChordGroup> getCombinedChordGroups(ArrayList<ChordGroup> difLevels,ArrayList<ChordGroup> others){
        ArrayList<ChordGroup> combined = new ArrayList<>(difLevels);
        combined.addAll(others);
        return combined;
    }

    public ArrayList<SingularMusicItemDm> getAllChords(){
        String jsonPath = "chord.json";
        String jsonString = JsonDataRetrieval.loadJSONFromAsset(getContext(),jsonPath);
        gson = new Gson();
        ArrayList<SingularMusicItemDm> singularMusicItemDms = gson.fromJson(jsonString, new TypeToken<ArrayList<SingularMusicItemDm>>(){}.getType());
        return singularMusicItemDms;
    }

    public void setAllMusicalItems(ArrayList<ChordGroup> combinedChordGroups,ArrayList<SingularMusicItemDm> allChords){
        this.musicItemDataModels.addAll(combinedChordGroups);
        this.musicItemDataModels.addAll(allChords);
    }

    public void FillChordGroups(ArrayList<SingularMusicItemDm> allChords,ArrayList<ChordGroup> difLevelGroup, ArrayList<ChordGroup> otherGroups){







    }




    public void setDatatoRecycler(){
        ChordLibRecyclerViewAdapter cvLibRecyclerAdapter = new ChordLibRecyclerViewAdapter(this.chordDataModelList,this);
        recyclerView.setAdapter(cvLibRecyclerAdapter);
    }



    @Override
    public void onRcViewClick(int position) {
        Toast.makeText(getContext(),"I've been clicked", Toast.LENGTH_SHORT).show();
    }
}