package com.example.overtone.data;

import java.util.ArrayList;

public class ChordGroup extends Chords {
     private String chordGroupName;
     private String chordGroupDescript;
     private ArrayList<SingularChordDm> chordList;

    public ChordGroup(String chordGroupName, ArrayList<SingularChordDm> chordList, int difficultyNumRating){
        this.chordGroupName = chordGroupName;
        this.chordList = chordList;
        super.setDifficultyNumRating(difficultyNumRating);
        if(chordList!=null &&this.chordList.size()>0){
            setChordDescription();
        }
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
    public void setChordDescription(){
        this.chordGroupDescript = getChordGroupDescript();
    }


    public String getChordGroupDescript() {
         return toString();
    }

    @Override
    public String toString() {
        if (chordList.size() > 0) {
            String res = chordList.get(0).toString() + ", ";
            for (int i = 1; i < chordList.size(); i++) {
                res += ", " + chordList.get(i).toString();
            }
            return res;
        }
       else return "";
    }

}
