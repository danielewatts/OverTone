package com.example.overtone.data;

public class GuitarString {
    private String name;
    private float frequency;

    public GuitarString(String stringName,Float tuningFrequency){
        this.name = stringName;
        this.frequency = tuningFrequency;
    }

    public String getName() {
        return name;
    }

    public float getFrequency() {
        return frequency;
    }
}
