package com.example.cs160_sp18.prog3;

import android.util.Pair;

public class Landmarks {

    public String name;
    public String file_name;
    public Pair<Double, Double> coordinates;

    Landmarks(String name, String file_name, Pair coordinates) {
        this.name = name;
        this.file_name = file_name;
        this.coordinates = coordinates;
    }
}
