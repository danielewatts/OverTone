package com.example.overtone.data;


import android.media.Image;

public class SingularChordDm extends Chords {
    private String chordName ;
    private Image chordDiagram;

    public SingularChordDm(){
        //default if need to set props later
    }

    public SingularChordDm(String chordName, int difficulty, Image chordDiagram ){
        if(difficulty<1){
            throw new IllegalArgumentException("Difficulty is 1 or above");
        }
        this.chordName = chordName;
        setDifficultyNumRating(difficulty);
        setDifficultyName(difficultyNumRating);
        this.chordDiagram = chordDiagram;
    }

    public Image getChordDiagram() {
        return chordDiagram;
    }

    public void setChordDiagram(Image chordDiagram) {
        this.chordDiagram = chordDiagram;
    }

    public String getChordName() {
        return chordName;
    }
    public void setChordName(String chordName) {
        this.chordName = chordName;
    }

    @Override
    public String toString() {
        return getChordName();
    }


}
