package com.hashcode;

public class Event implements Comparable<Event> {
    public static int START = 1;
    public static int FINISH = 0;
    public static int STRICT_START = 3; //start here or never
    public static int POSTPONED_START = 2; //no bonus here
    
    int type;
    int ride;
    int time;

    public Event(int type, int ride, int time) {
        this.type = type;
        this.ride = ride;
        this.time = time;
    }

    @Override
    public int compareTo(Event o) {
        int timeCmp = Integer.compare(time, o.time);
        if (timeCmp != 0) return timeCmp;
        return Integer.compare(type, o.type);
    }
}
