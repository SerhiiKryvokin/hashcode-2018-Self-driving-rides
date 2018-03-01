package com.hashcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    int rowsInGreed;
    int colInGreed;
    int vehNum;
    int ridNum;
    int bonus;
    int stepNum;

    Vehicle[] vehicles;
    Ride[] rides;

    Map<Integer, ArrayList<Integer>> vehicleRide = new HashMap<>();
    PriorityQueue<Event> eventsQueue = new PriorityQueue<>();
    PriorityQueue<Integer> finishTimes = new PriorityQueue<>();
    HashSet<Integer> assignedRides = new HashSet<>();
    
    void solve() {
        rowsInGreed = in.nextInt();
        colInGreed = in.nextInt();
        vehNum = in.nextInt();
        vehicles = new Vehicle[vehNum];
        for (int i = 0; i < vehNum; i++) {
            vehicles[i] = new Vehicle(0, 0, -1);
        }
        ridNum = in.nextInt();
        rides = new Ride[ridNum];

        bonus = in.nextInt();
        stepNum = in.nextInt();

        for (int i = 0; i < ridNum; i++) {
            int rowStart = in.nextInt();
            int colStart = in.nextInt();
            int rowFinish = in.nextInt();
            int colFinish = in.nextInt();
            int earliest = in.nextInt();
            int latest = in.nextInt();
            rides[i] = new Ride(rowStart, colStart, rowFinish, colFinish, earliest, latest, -1);
            int stricStart = getStrictStart(i);
            rides[i].strictStart = stricStart;
            int realEarliest = Math.max(rowStart + colStart, earliest);
            if (stricStart < 0) {
                throw new RuntimeException();
            }
            if (stricStart < realEarliest) {
                System.out.println("oops");
                continue;
            }
            if (stricStart != realEarliest) {
                eventsQueue.add(new Event(Event.START, i, realEarliest));
            }
            eventsQueue.add(new Event(Event.STRICT_START, i, getStrictStart(i)));
        }
        while (!eventsQueue.isEmpty()) {
            Event e = eventsQueue.poll();
            if (e.type == Event.FINISH) {
                if (rides[e.ride].vehicle == -1) {
                    continue;
                }
                vehicles[rides[e.ride].vehicle].ride = -1;
                vehicles[rides[e.ride].vehicle].col = rides[e.ride].colFinish;
                vehicles[rides[e.ride].vehicle].row = rides[e.ride].rowFinish;
                vehicles[rides[e.ride].vehicle].freeTime = e.time;

                finishTimes.poll();
                continue;
            }
            if (assignedRides.contains(e.ride))
                continue;
            assignVehicle(e);
        }
        printResult();
    }

    private int getStrictStart(int ride) {
        Ride r = rides[ride];
        return r.latest - (Math.abs(r.rowStart - r.rowFinish) + Math.abs(r.colStart - r.colFinish));
    }

    private void assignVehicle(Event e) {
        int ride = e.ride;
        int bestVehicle = getClosestVehicle(ride, e.time);
        if (bestVehicle == -1) {
            if (e.type == Event.POSTPONED_START || e.type == Event.START) {
                int nextTime = e.time + 1;
                if (finishTimes.size() == vehNum) {
                    nextTime = finishTimes.peek();
                }
                if (nextTime < rides[e.ride].strictStart) {
                    eventsQueue.add(new Event(Event.POSTPONED_START, e.ride, nextTime));
                }
            }
            return;
        }
        vehicles[bestVehicle].ride = ride;
        rides[ride].vehicle = bestVehicle;
        vehicleRide.computeIfAbsent(bestVehicle, k -> new ArrayList<>());
        vehicleRide.get(bestVehicle).add(ride);

        int len = getLen(ride);
        int dist = dist(ride, bestVehicle);
        int arrive = vehicles[bestVehicle].freeTime + dist;
        eventsQueue.add(new Event(Event.FINISH, ride, Math.max(arrive, e.time) + len));
        finishTimes.add(Math.max(arrive, e.time) + len);
//        System.out.println("time: " + e.time + " assigned vehicle: " + bestVehicle + " to ride " + ride);
        assignedRides.add(ride);
    }

    private int getLen(int ride) {
        return Math.abs(rides[ride].colStart - rides[ride].colFinish)
                + Math.abs(rides[ride].rowStart - rides[ride].rowFinish);
    }


    private int distFin(int ride, int bestVehicle) {
        return Math.abs(rides[ride].colFinish - vehicles[bestVehicle].col)
                + Math.abs(rides[ride].rowFinish - vehicles[bestVehicle].row);
    }

    //that will be in time
    private int getClosestVehicle(int ride, int time) {
        Ride r = rides[ride];
        int bestVeh = -1;
        int bestArrive = -1;
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i].ride != -1)
                continue;
            int dist = dist(ride, i);
            int arrive = vehicles[i].freeTime + dist;
            if (time < arrive) {
                continue;
            }
            if (bestArrive == -1 || bestArrive > arrive) {
                bestArrive = arrive;
                bestVeh = i;
            }
        }
        return bestVeh;
    }

    private int dist(int ride, int vehicle) {
        return Math.abs(rides[ride].colStart - vehicles[vehicle].col)
                + Math.abs(rides[ride].rowStart - vehicles[vehicle].row);

    }

    private void printResult() {
        int total = 0;
        for (int i = 0; i < vehNum; i++) {
            out.print(vehicleRide.get(i).size() + " ");
            total += vehicleRide.get(i).size();
            for (int ride : vehicleRide.get(i)) {
                out.print(ride + " ");
            }
            out.println();
        }
//        System.out.println(total);
    }


    Scanner in;
    PrintWriter out;

    void run() {
        try {
            in = new Scanner(new File("A.in"));
            out = new PrintWriter(new File("A.out"));

            solve();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void runIO() {

        in = new Scanner(System.in);
        out = new PrintWriter(System.out);

        solve();

        out.close();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}