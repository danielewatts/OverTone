package com.example.overtone.data;


import android.media.Image;

public class SingularChordDm {
    private String chordName ;
    private Image chordDiagram;
    private boolean openChord;
    private boolean popularChord;
    private boolean barChord;
    private DifficultyLevel diffLevel;





    public SingularChordDm(){
        //default if need to set props later
    }
//    public SingularChordDm(String chordName, DifficultyLevel difficultLvl){
//        this.chordName = chordName;
//        this.diffLevel = difficultLvl;
//    }
    public SingularChordDm(String chordName, boolean bar){
        this.chordName = chordName;
        this.barChord = true;
    }

    public String getChordName(String chordName){
        return this.chordName;
    }


}
