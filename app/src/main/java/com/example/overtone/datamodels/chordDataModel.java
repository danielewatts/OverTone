package com.example.overtone.datamodels;


import android.media.Image;

public class chordDataModel {
    private String chordName ;
    private int difficulty;
    private Image chordDiagram;

    public chordDataModel(){
        //default if need to set props later
    }
    public chordDataModel(String chordName, int difficulty, Image chordDiagram ){
        if(difficulty<1){
            throw new IllegalArgumentException("Difficulty is 1 or above");
        }
        this.chordName = chordName;
        this.difficulty = difficulty;
        this.chordDiagram = chordDiagram;
    }

    public Image getChordDiagram() {
        return chordDiagram;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getChordName() {
        return chordName;
    }

    public void setChordDiagram(Image chordDiagram) {
        this.chordDiagram = chordDiagram;
    }

    public void setChordName(String chordName) {
        this.chordName = chordName;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }



}
