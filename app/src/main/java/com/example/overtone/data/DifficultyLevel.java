package com.example.overtone.data;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public enum DifficultyLevel {
    @SerializedName("0")
    Easy("Beginner"),
    @SerializedName("1")
    Medium("Intermediate"),
    @SerializedName("2")
    Hard("Advanced");
    private String strName;

    DifficultyLevel(String stringName) {
        this.strName = stringName;
    }
    public String getStrName() {
        return strName;
    }

}


