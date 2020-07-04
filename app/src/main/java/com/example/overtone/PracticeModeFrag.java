package com.example.overtone;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.overtone.data.SingleChord;

import java.util.ArrayList;

public class PracticeModeFrag extends Fragment implements View.OnClickListener{


    private Button dialogOpener;
    private String[] listItems;
    private boolean[] checkedItems;
    private TextView chordsSelected;
    private TextView bpmStringRep;
    private ArrayList<SingleChord> singleChords = MainActivity.getAllSingleChords();
    private ArrayList<Integer> selectedChordNames = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice_mode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBtns(view);
        setTextViews(view);
        setListItems();
        chordsSelected = view.findViewById(R.id.chordsInRotation);
        checkedItems = new boolean[listItems.length];


    }



    public void setBtns(View view){
        dialogOpener = view.findViewById(R.id.openChordDialogBtn);
        dialogOpener.setOnClickListener(this);
    }
    public void setTextViews(View view){
        chordsSelected = view.findViewById(R.id.chordsInRotation);
        bpmStringRep = view.findViewById(R.id.bpmDisplay);
    }

    public void setListItems(){
        ArrayList<String> chordNames = new ArrayList<>();
        for (SingleChord sg: singleChords) {
            chordNames.add(sg.getName());
        }
        listItems = chordNames.toArray(new String[0]);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.openChordDialogBtn:
                alertDialogAction();
                break;
        }
    }


    public void alertDialogAction(){
        AlertDialog.Builder mBuilder  = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Chords available to be selected");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if(isChecked){
                    selectedChordNames.add(position);
                }else{
                    selectedChordNames.remove((Integer.valueOf(position)));
                }
            }

        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < selectedChordNames.size(); i++) {
                    item = item + listItems[selectedChordNames.get(i)];
                    if (i != selectedChordNames.size() - 1) {
                        item = item + ", ";
                    }
                }
                chordsSelected.setText(item);
            }
        });

        mBuilder.setNegativeButton("GONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clearing All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    selectedChordNames.clear();
                    chordsSelected.setText("");

                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }




}