package com.example.overtone.screens;
import android.media.SoundPool;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.overtone.R;
import com.example.overtone.metronomePlayer.Metronome;

public class HomeTuneFrag extends Fragment implements View.OnClickListener {
    NavController navController = null;
    private SoundPool soundPool;
    private int sound1;
    private Metronome joe;
    private int count = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null){
            count = savedInstanceState.getInt("ourkey",4);
            System.out.println("INSIDE OF ONCREATE: " + count  );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_tune_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            TextView txtV = view.findViewById(R.id.homeTuneTxt);
            setBtns(view);
            joe = new Metronome(getContext());
            txtV.setText(count + "");
            //debug
            System.out.println("View created homeTuneFrag");
            //
        }
    }

    private void setBtns(View view){
        Button btn = view.findViewById(R.id.tuneBtn);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tuneBtn:
                joe.makeGameSound();
                count++;
                Toast.makeText(getContext(),"MAKING SOUND ?",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState called");
        outState.putInt("ourkey",count);
    }
}