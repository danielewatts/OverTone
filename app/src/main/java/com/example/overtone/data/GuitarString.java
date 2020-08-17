package com.example.overtone.data;

public class GuitarString {
    private String name;
    private float frequency;
    private float maxFrequency;
    private float minFrequency;

    public GuitarString(String stringName,Float tuningFrequency){
        this.name = stringName;
        this.frequency = tuningFrequency;
        setFreqRange();
    }

    public String getName() {
        return this.name;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFreqRange(){
        this.maxFrequency = this.frequency + 8.00F;
        this.minFrequency = this.frequency - 8.00F;
    }

    public float getMaxFrequency() {
        return this.maxFrequency;
    }

    public float getMinFrequency() {
        return this.minFrequency;
    }
}
