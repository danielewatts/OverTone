package com.example.overtone.data;

import java.util.ArrayList;

public class ChordGroup  {
     private String chordGroupName;
     private String chordGroupDescript;
     private DifficultyLevel chrdGroupDiffLvl;
     private ArrayList<SingularChordDm> chordList;

    public ChordGroup(String chordGroupName, String chordGroupDescript,DifficultyLevel chrdGroupDiffLvl,ArrayList<SingularChordDm> chordList){
        this.chordGroupName = chordGroupName;
        this.chordGroupDescript = chordGroupDescript;
        this.chrdGroupDiffLvl = chrdGroupDiffLvl;
        this.chordList = chordList;
    }

    public ChordGroup(String chordGroupName){
        this.chordGroupName = chordGroupName;
    }

    public String getChordGroupDescript() {
        if (chordList.size() > 0) {
            String res = chordList.get(0).toString() + ", ";
            for (int i = 1; i < chordList.size(); i++) {
                res += ", " + chordList.get(i).toString();
            }
            return res;
        }
        else return "";
    }

    @Override
    public String toString() {
        return "ChordGroup{" +
                "chordGroupName='" + chordGroupName + '\'' +
                ", chordGroupDescript='" + chordGroupDescript + '\'' +
                ", chrdGroupDiffLvl=" + chrdGroupDiffLvl +
                ", chordList=" + chordList +
                '}';
    }

}
