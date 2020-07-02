package com.example.overtone.data;


import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.overtone.R;

public class SingleChord implements MusicItem,Parcelable {
    private String chordName ;
    private boolean openChord;
    private boolean popularChord;
    private boolean barChord;
    private DifficultyLevel diffLevel;
    ///chordDiagram Property will be set after construction of obj
    private Integer chordDiagram;

    public SingleChord(String chordName, boolean bar, boolean openCh, boolean popCh, DifficultyLevel difficultyLevel){
        this.chordName = chordName;
        this.barChord = bar;
        this.openChord = openCh;
        this.popularChord = popCh;
        this.diffLevel = difficultyLevel;
    }

    protected SingleChord(Parcel in) {
        chordName = in.readString();
        openChord = in.readByte() != 0;
        popularChord = in.readByte() != 0;
        barChord = in.readByte() != 0;
        if (in.readByte() == 0) {
            chordDiagram = null;
        } else {
            chordDiagram = in.readInt();
        }
    }

    public static final Creator<SingleChord> CREATOR = new Creator<SingleChord>() {
        @Override
        public SingleChord createFromParcel(Parcel in) {
            return new SingleChord(in);
        }

        @Override
        public SingleChord[] newArray(int size) {
            return new SingleChord[size];
        }
    };

    public Integer getChordDiagram() {
        return this.chordDiagram;
    }

    public void setChordDiagram(Integer chordDiagramId) {
        this.chordDiagram = chordDiagramId;
    }

    public String getChordName() {
        return this.chordName;
    }


    public boolean isBarChord() {
        return this.barChord;
    }

    public boolean isOpenChord() {
        return this.openChord;
    }

    public boolean isPopularChord() {
        return this.popularChord;
    }

    public DifficultyLevel getDiffLevel() {
        return this.diffLevel;
    }

    @Override
    public String toString() {
        return "SingularChordDm{" +
                "chordName='" + chordName + '\'' +
                ", chordDiagram=" + chordDiagram +
                ", openChord=" + openChord +
                ", popularChord=" + popularChord +
                ", barChord=" + barChord +
                ", diffLevel=" + diffLevel +
                '}';
    }

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
        return getChordDiagram();
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
        if (chordDiagram == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(chordDiagram);
        }
    }
}
