package com.example.overtone.data;

public abstract class Chords {

    protected int difficultyNumRating;
    protected String difficultyName;
    public abstract String toString();
    public int getDifficultyNumRating(){
        return difficultyNumRating;
    }
    public void setDifficultyNumRating(int difNumRating){
        this.difficultyNumRating =difNumRating;
    }

//    public void setDifficultyName(int difficultyNumRating){
//        this.difficultyName = assignDifficultyName(difficultyNumRating);
//
//    }
    public void setDifficultyName(int difficultyNumRating){
        if(difficultyNumRating<1 || difficultyNumRating > 4)
        {
            throw new IllegalArgumentException("to small of difficulty range or too big (1-4 inclusive only)");
        }
        switch (difficultyNumRating){
            case 1:
                this.difficultyName = "Beginner";
                break;
            case 2:
                this.difficultyName = "Intermediate";
                break;
            case 3:
                this.difficultyName = "Hard";
                break;
            case 4:
                this.difficultyName = "Advanced";
                break;
        }
    }
    public String getDifficultyName(){
        return this.difficultyName;
    }





}
