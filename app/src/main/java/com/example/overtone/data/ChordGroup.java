package com.example.overtone.data;

import com.example.overtone.R;

import java.util.ArrayList;

public class ChordGroup implements MusicItem {
     private static final String groupTag = " chords";
     private String chordGroupName;
     private String chordGroupDescript;
     private DifficultyLevel chrdGroupDiffLvl;
     private ArrayList<SingularMusicItemDm> chordList;
     private Integer imageId;
     private Integer testImageID = R.drawable.spicychile;

    public ChordGroup(String chordGroupName, String chordGroupDescript, DifficultyLevel chrdGroupDiffLvl, ArrayList<SingularMusicItemDm> chordList){
        setChordGroupName(chordGroupName);
        this.chordGroupDescript = chordGroupDescript;
        this.chrdGroupDiffLvl = chrdGroupDiffLvl;
        this.chordList = chordList;
    }

    public ChordGroup(String chordGroupName){
        setChordGroupName(chordGroupName);
        setImageId(testImageID);
        this.chordList = new ArrayList<SingularMusicItemDm>();
    }

    public String getGroupMakeUp() {
        String res = "";
        String spacing = " , ";
        if(this.chordList.size()>0){
            res = chordList.get(0).getChordName();
            for(int i = 1; i <chordList.size()-1; i++ ){
                res = res + spacing + chordList.get(i).getChordName();
            }
            res = res + spacing + chordList.get(chordList.size()-1).getChordName();

        }
        return res;
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
        this.chordGroupName = chordGroupName + groupTag;
    }

    public void setChrdGroupDiffLvl(DifficultyLevel chrdGroupDiffLvl) {
        this.chrdGroupDiffLvl = chrdGroupDiffLvl;
    }


    public void setImageId(Integer resFileTag) {
        this.imageId = resFileTag;
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

    @Override
    public String getName() {
        return getGroupMakeUp();
    }

    @Override
    public String getDescription() {
        return getChordGroupName();
    }

    @Override
    public int getImageID() {
        return imageId;
    }
}
