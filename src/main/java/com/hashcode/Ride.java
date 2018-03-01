package com.hashcode;

public class Ride {
    int rowStart;
    int colStart;
    int rowFinish;
    int colFinish;
    int earliest;
    int latest;
    int vehicle;
    int strictStart;

    public Ride(int rowStart, int colStart, int rowFinish, int colFinish, int earliest, int latest, int vehicle) {
        this.rowStart = rowStart;
        this.colStart = colStart;
        this.rowFinish = rowFinish;
        this.colFinish = colFinish;
        this.earliest = earliest;
        this.latest = latest;
        this.vehicle = vehicle;
    }
}
