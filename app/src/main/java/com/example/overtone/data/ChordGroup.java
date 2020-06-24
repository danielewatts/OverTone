package com.example.overtone.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.overtone.R;

import java.util.ArrayList;

public class ChordGroup implements MusicItem, Parcelable {
     private static final String groupTag = " chords";
     private String chordGroupName;
     private String chordGroupDescript;
     private DifficultyLevel chrdGroupDiffLvl;
     private ArrayList<SingleChord> chordList;
     private Integer imageId;
     private Integer testImageID = R.drawable.spicychile;

    public ChordGroup(String chordGroupName, String chordGroupDescript, DifficultyLevel chrdGroupDiffLvl, ArrayList<SingleChord> chordList){
        setChordGroupName(chordGroupName);
        this.chordGroupDescript = chordGroupDescript;
        this.chrdGroupDiffLvl = chrdGroupDiffLvl;
        this.chordList = chordList;
    }

    public ChordGroup(String chordGroupName){
        setChordGroupName(chordGroupName);
        this.chordList = new ArrayList<>();
    }

    protected ChordGroup(Parcel in) {
        chordGroupName = in.readString();
        chordGroupDescript = in.readString();
        if (in.readByte() == 0) {
            imageId = null;
        } else {
            imageId = in.readInt();
        }
        if (in.readByte() == 0) {
            testImageID = null;
        } else {
            testImageID = in.readInt();
        }
    }

    public static final Creator<ChordGroup> CREATOR = new Creator<ChordGroup>() {
        @Override
        public ChordGroup createFromParcel(Parcel in) {
            return new ChordGroup(in);
        }

        @Override
        public ChordGroup[] newArray(int size) {
            return new ChordGroup[size];
        }
    };

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

    public void setChordList(ArrayList<SingleChord> chordList) {
        this.chordList = chordList;
    }
    public void addToChordList(SingleChord chord){
        this.chordList.add(chord);
    }

    public ArrayList<SingleChord> getChordList() {
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


    public void setImageId() {
//        this.imageId = resFileTag;
        switch(chrdGroupDiffLvl){
            case Easy:
                this.imageId = R.drawable.guitars;
                break;
            case Medium:
                this.imageId = R.drawable.spicychile;
                break;
            case Hard:
                this.imageId = R.drawable.chilepep;
                break;
        }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chordGroupName);
        dest.writeString(chordGroupDescript);
        if (imageId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageId);
        }
        if (testImageID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(testImageID);
        }
    }
}
