package com.example.overtone.data;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public enum DifficultyLevel {
    @SerializedName("0")
    Easy("Easy"),
    @SerializedName("1")
    Medium("Medium"),
    @SerializedName("2")
    Hard("Hard"),
    @SerializedName("3")
    Advanced("Advanced");

//    private final int value;
//    public int getValue() {
//        return ;
//    }

//    private DifficultyLevel(int value) {
//        this.value = value;
//    }
    private String strName;

    DifficultyLevel(String stringName) {
        this.strName = stringName;
    }
    public String getStrName() {
        return strName;
    }

}


