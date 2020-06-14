package com.example.overtone.data;


import android.media.Image;

public class SingularChordDm {
    private String chordName ;
    private Image chordDiagram;
    private boolean openChord;
    private boolean popularChord;
    private boolean barChord;
    private DifficultyLevel diffLevel;

    public SingularChordDm(String chordName, boolean bar,boolean openC,boolean popCh,DifficultyLevel difficultyLevel){
        this.chordName = chordName;
        this.barChord = bar;
        this.openChord = openC;
        this.popularChord = popCh;
        this.diffLevel = difficultyLevel;
    }

    public String getChordName(String chordName){
        return this.chordName;
    }

    public boolean isBarChord() {
        return barChord;
    }

    public boolean isOpenChord() {
        return openChord;
    }

    public boolean isPopularChord() {
        return popularChord;
    }

    public DifficultyLevel getDiffLevel() {
        return diffLevel;
    }

}
