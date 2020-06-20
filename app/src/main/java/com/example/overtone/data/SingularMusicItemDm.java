package com.example.overtone.data;


import android.media.Image;

public class SingularMusicItemDm implements MusicItem {
    private String chordName ;
    private Image chordDiagram;
    private boolean openChord;
    private boolean popularChord;
    private boolean barChord;
    private DifficultyLevel diffLevel;

    public SingularMusicItemDm(String chordName, boolean bar, boolean openCh, boolean popCh, DifficultyLevel difficultyLevel){
        this.chordName = chordName;
        this.barChord = bar;
        this.openChord = openCh;
        this.popularChord = popCh;
        this.diffLevel = difficultyLevel;
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

}
