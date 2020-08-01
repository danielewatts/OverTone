package com.example.overtone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.overtone.data.ChordGroup;
import com.example.overtone.recyclerview.ChordLibRecyclerViewAdapter;
import com.example.overtone.recyclerview.RecyclerViewClickListener;

import java.util.ArrayList;


public class ChordLibraryFrag extends Fragment implements RecyclerViewClickListener {


    /**Custom input recyclerView class*/
    private RecyclerView recyclerView;
    private ArrayList<ChordGroup> chordGroups;
    private final int SPACE_ORIENTATION_TAG = 1;
    //space type can either be 1 for vertical spacing or 0 for horizontal spacing//
    private NavController navController;

    public ChordLibraryFrag() {
        // Required empty public constructor
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
        defineRecyclerView(view);
        /** need to fix chordGroups getting remade with duplicates everytime fragment is loaded up
         * aka a bottomNav Fragment state saving problem
         * */
        setChordGroups();
        setVerticalItemSpace(SPACE_ORIENTATION_TAG);
        setDatatoRecycler();
    }

    public void defineRecyclerView(View v){
        recyclerView = v.findViewById(R.id.chord_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
    }

    public void setDatatoRecycler(){
        ChordLibRecyclerViewAdapter cvLibRecyclerAdapter = new ChordLibRecyclerViewAdapter(this.chordGroups,this);
        this.recyclerView.setAdapter(cvLibRecyclerAdapter);
    }

    public void setVerticalItemSpace(int spacing){
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), spacing);
        this.recyclerView.addItemDecoration(itemDecor);
    }
    public void setChordGroups(){
        this.chordGroups = MainActivity.Companion.getAllChordGroups();
    }


    @Override
    public void onRcViewClick(int position) {
        this.navController = Navigation.findNavController(this.recyclerView);
        ChordGroup clickedOnCg = this.chordGroups.get(position);
        // acquired chordGroup card that has been clicked on, now send it using safeArgs
//        ChordLibraryFragDirections.ActionChordLibraryFragToFragmentGroupDetails action = ChordLibraryFragDirections.
//                actionChordLibraryFragToFragmentGroupDetails(clickedOnCg);
        ChordLibraryFragDirections.ActionChordLibraryFragToChordGroupDetailsFrag action = ChordLibraryFragDirections.actionChordLibraryFragToChordGroupDetailsFrag(clickedOnCg);
        //sending safeArgs data with navController to next fragment
        //add transition here ?
        this.navController.navigate(action);
    }


}