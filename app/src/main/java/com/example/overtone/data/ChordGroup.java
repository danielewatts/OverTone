package com.example.overtone.data;

import java.util.ArrayList;

public class ChordGroup implements MusicItem {
     private String chordGroupName;
     private String chordGroupDescript;
     private DifficultyLevel chrdGroupDiffLvl;
     private ArrayList<SingularMusicItemDm> chordList;

    public ChordGroup(String chordGroupName, String chordGroupDescript, DifficultyLevel chrdGroupDiffLvl, ArrayList<SingularMusicItemDm> chordList){
        this.chordGroupName = chordGroupName;
        this.chordGroupDescript = chordGroupDescript;
        this.chrdGroupDiffLvl = chrdGroupDiffLvl;
        this.chordList = chordList;
    }

    public ChordGroup(String chordGroupName){
        this.chordGroupName = chordGroupName;
        this.chordList = new ArrayList<SingularMusicItemDm>();
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
    public String getChordGroupName(){
        return this.chordGroupName;
    }

    public void setChordList(ArrayList<SingularMusicItemDm> chordList) {
        this.chordList = chordList;
    }
    public void addToChordList(SingularMusicItemDm chord){
        this.chordList.add(chord);
    }

    public ArrayList<SingularMusicItemDm> getChordList() {
        return chordList;
    }

    public DifficultyLevel getChrdGroupDiffLvl() {
        return chrdGroupDiffLvl;
    }

    public void setChordGroupName(String chordGroupName) {
        this.chordGroupName = chordGroupName;
    }

    public void setChrdGroupDiffLvl(DifficultyLevel chrdGroupDiffLvl) {
        this.chrdGroupDiffLvl = chrdGroupDiffLvl;
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
