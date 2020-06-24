package com.example.overtone.data;


import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.overtone.R;

public class SingularMusicItemDm implements MusicItem, Parcelable {
    private String chordName ;
    private Image chordDiagram;
    private boolean openChord;
    private boolean popularChord;
    private boolean barChord;
    private DifficultyLevel diffLevel;
    private Integer testImg = R.drawable.spicychile;



    public SingularMusicItemDm(String chordName, boolean bar, boolean openCh, boolean popCh, DifficultyLevel difficultyLevel){
        this.chordName = chordName;
        this.barChord = bar;
        this.openChord = openCh;
        this.popularChord = popCh;
        this.diffLevel = difficultyLevel;
        setTestImageID(R.drawable.spicychile);
    }

    protected SingularMusicItemDm(Parcel in) {
        chordName = in.readString();
        openChord = in.readByte() != 0;
        popularChord = in.readByte() != 0;
        barChord = in.readByte() != 0;
        if (in.readByte() == 0) {
            testImg = null;
        } else {
            testImg = in.readInt();
        }
    }

    public static final Creator<SingularMusicItemDm> CREATOR = new Creator<SingularMusicItemDm>() {
        @Override
        public SingularMusicItemDm createFromParcel(Parcel in) {
            return new SingularMusicItemDm(in);
        }

        @Override
        public SingularMusicItemDm[] newArray(int size) {
            return new SingularMusicItemDm[size];
        }
    };

    public Integer getTestImageID(){
        return testImg;
    }

    public void setTestImageID(Integer spicychile) {
        testImg = spicychile;
    }


    public String getChordName(String chordName){
        return this.chordName;
    }

    public String getChordName() {
        return chordName;
    }

    public Image getChordDiagram() {
        return chordDiagram;
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

//    @Override
//    public String toString() {
//        return "SingularChordDm{" +
//                "chordName='" + chordName + '\'' +
//                ", chordDiagram=" + chordDiagram +
//                ", openChord=" + openChord +
//                ", popularChord=" + popularChord +
//                ", barChord=" + barChord +
//                ", diffLevel=" + diffLevel +
//                '}';
//    }

    @Override
    public String getName() {
        return getChordName();
    }

    @Override
    public String getDescription() {
        return this.getDiffLevel().getStrName();
    }

    @Override
    public int getImageID() {
        return getTestImageID();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chordName);
        dest.writeByte((byte) (openChord ? 1 : 0));
        dest.writeByte((byte) (popularChord ? 1 : 0));
        dest.writeByte((byte) (barChord ? 1 : 0));
        if (testImg == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(testImg);
        }
    }
}
