package com.example.overtone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.overtone.data.SingleChord;

import java.util.ArrayList;

public class PracticeModeFrag extends Fragment implements View.OnClickListener{


    Button dialogOpener;
    String[] listItems;
    boolean[] checkedItems;
    TextView chordsSelected;
    ArrayList<SingleChord> singleChords = MainActivity.getAllSingleChords();


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
        chordsSelected = view.findViewById(R.id.chordsInRotation);
//        listItems = singleChords.toArray(Strin);
//        for (int i = 0; i < singleChords.size() ; i++) {
//
//        }


    }



    public void setBtns(View view){
        dialogOpener = (Button)view.findViewById(R.id.openChordDialogBtn);
        dialogOpener.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.openChordDialogBtn:

                break;
        }


    }
}