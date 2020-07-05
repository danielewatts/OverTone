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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.overtone.data.SingleChord;

import java.util.ArrayList;

public class PracticeModeFrag extends Fragment implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{
    private Button dialogOpener;
    private String[] listItems;
    private boolean[] checkedItems;
    private TextView chordsSelected;
    private TextView bpmUnitTag;
    private EditText bpmRep;
    private ArrayList<SingleChord> singleChords = MainActivity.getAllSingleChords();
    private ArrayList<Integer> selectedChordNames = new ArrayList<>();
    private SeekBar seekBar;
    private final int STARTING_BPM_VAL = 200;
    private final String STARTING_BPM_REP = "BPM: " + STARTING_BPM_VAL;
    private final int MAX_BMP_VAL = 400;
    private int currentBpm;


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
        setUpSeekBar(view);
        setEditTexts(view);
        chordsSelected = view.findViewById(R.id.chordsInRotation);
        checkedItems = new boolean[listItems.length];
    }


    public void setBtns(View view){
        dialogOpener = view.findViewById(R.id.openChordDialogBtn);
        dialogOpener.setOnClickListener(this);
    }
    public void setTextViews(View view){
        chordsSelected = view.findViewById(R.id.chordsInRotation);
//        displayedBpm = view.findViewById(R.id.BpmHeader);
//        displayedBpm.setText(STARTING_BPM_REP);
    }
    public void setEditTexts(View view){
        bpmRep = view.findViewById(R.id.BpmEntry);
        bpmRep.setText(""+STARTING_BPM_VAL);

    }

    public void setListItems(){
        ArrayList<String> chordNames = new ArrayList<>();
        for (SingleChord sg: singleChords) {
            chordNames.add(sg.getName());
        }
        listItems = chordNames.toArray(new String[0]);
    }


    public void setUpSeekBar(View view){
        seekBar = view.findViewById(R.id.SeekBarBpm);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(MAX_BMP_VAL);
        seekBar.setProgress(STARTING_BPM_VAL);
        currentBpm = STARTING_BPM_VAL;
    }








    @Override
    //onClick listener for dialog list and operator
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.openChordDialogBtn:
                startChordDialog();
                break;
        }
    }


    public void startChordDialog(){
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


    @Override
    ///tracking change in BPM to the user
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int currentBpm = progress;
//        this.displayedBpm.setText("BPM: " + currentBpm);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}