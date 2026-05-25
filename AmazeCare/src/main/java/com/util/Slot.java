package com.util;

import java.time.LocalTime;

public class Slot {
    private LocalTime startTime;
    private LocalTime endTime;
    private int duration;

    public Slot() {
    }

    public Slot(LocalTime startTime, LocalTime endTime, int duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return startTime + " to "+ endTime;
    }
}
