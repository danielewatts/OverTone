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
import com.example.overtone.data.DataCreation;
import com.example.overtone.recyclerview.ChordLibRecyclerViewAdapter;
import com.example.overtone.recyclerview.RecyclerViewClickListener;

import java.util.ArrayList;


public class ChordLibraryFrag extends Fragment implements RecyclerViewClickListener {


    /**Custom input recyclerView class*/
    private RecyclerView recyclerView;
    private ArrayList<ChordGroup> chordGroups;
    private static final int SPACE_TYPE_TAG = 1;
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
        recyclerView = view.findViewById(R.id.chord_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);
        /** need to fix chordGroups getting remade with duplicates everytime fragment is loaded up */
        this.chordGroups = DataCreation.getCreatedGroups(getContext());
        setVerticalItemSpace(SPACE_TYPE_TAG);
        setDatatoRecycler();
    }



    public void setDatatoRecycler(){
        ChordLibRecyclerViewAdapter cvLibRecyclerAdapter = new ChordLibRecyclerViewAdapter(this.chordGroups,this);
        recyclerView.setAdapter(cvLibRecyclerAdapter);
//        DividerItemDecoration
    }
    public void setVerticalItemSpace(int spacing){
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), spacing);
        recyclerView.addItemDecoration(itemDecor);
    }



    @Override
    public void onRcViewClick(int position) {
        Toast.makeText(getContext(),"I've been clicked", Toast.LENGTH_SHORT).show();
        navController = Navigation.findNavController(recyclerView);
        //passing objects to the view through nav controller and creating an "action"
        ChordLibraryFragDirections.ActionChordLibraryFragToFragmentGroupDetails action = ChordLibraryFragDirections.actionChordLibraryFragToFragmentGroupDetails(listToArray(this.chordGroups));
        navController.navigate(action);
    }


    public ChordGroup[] listToArray(ArrayList<ChordGroup> list){
        /**needs major refactoring to avoid all these type shannigans*/
        ChordGroup[] vals = new ChordGroup[list.size()];
        int index = 0;
        for (ChordGroup cg: list) {
                vals[index] = cg;
            index++;
        }
        return vals;
    }

}