package com.example.overtone.data;

import java.util.ArrayList;
import java.util.List;

public class ChordGroup extends Chords {
     private String chordGroupName;
     private String chordDescription;
     private ArrayList<SingularChordDm> chordList;

    public ChordGroup(String chordGroupName, ArrayList<SingularChordDm> chordList, int difficultyNumRating){
        this.chordGroupName = chordGroupName;
        this.chordList = chordList;
        super.setDifficultyNumRating(difficultyNumRating);
    }
    public void setChordGroupName(String chordGroupName){
        this.chordGroupName = chordGroupName;
    }
//    public void addToChordList(SingularChordDm chordDm){
//        this.chordList.add(chordDm);
//    }
//    public void removeFromChordList(SingularChordDm chordDm){
//        this.chordList.remove(chordDm);
//    }
    public ArrayList<SingularChordDm> getChordList(){
        return chordList;
    }


    public String getChordDescription() {
         return toString();
    }

    @Override
    public String toString() {
        String res =chordList.get(0).toString() + ", ";
        for (int i = 1; i < chordList.size() ; i++) {
            res += ", " + chordList.get(i).toString();
        }
        return res;
    }
}
