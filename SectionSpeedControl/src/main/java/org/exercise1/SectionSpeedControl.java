package org.exercise1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SectionSpeedControl {
    private final List<DataLine> measurements = new ArrayList<>();
    private final double lengthKm;
    private final int speedLimit;

    private static final int hourMs = 60 * 60 * 1000;

    SectionSpeedControl(double distance, int speedLimit) {
        this.lengthKm = distance;
        this.speedLimit = speedLimit;
    }

    void exercise1(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            DataLine dl = new DataLine(line);
            measurements.add(dl);
            line = reader.readLine();
        }
        reader.close();
    }

    void exercise2() {
        System.out.println("Exercise 2.");
        System.out.printf("The data of %d vehicles were recorded in the measurement.%n%n", measurements.size());
    }

    void exercise3(int hour) {
        System.out.println("Exercise 3.");
        int timeMs = hour * hourMs;
        int counter = 0;
        for (DataLine dl : measurements) {
            if (dl.exitTimeMs < timeMs)
                ++counter;
        }
        System.out.printf("Before %d o'clock %d vehicles passed the exit point recorder.%n%n", hour, counter);
    }

    void exercise4() throws IOException {
        System.out.println("Exercise 4.");
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        System.out.print("Enter an hour and minute value: ");
        String input = br.readLine();
        String[] splitLine = input.split(" ");
        if (splitLine.length != 2)
            throw new IOException("Invalid input!");
        int hour = Integer.parseInt(splitLine[0]);
        int minute = Integer.parseInt(splitLine[1]);
        int startTimeMs = (minute + 60 * hour) * 60000;
        int endTimeMs = startTimeMs + 60000;
        int entryCounter = 0;
        int intensityCounter = 0;
        for (DataLine dl : measurements) {
            if (dl.entranceTimeMs >= startTimeMs && dl.entranceTimeMs < endTimeMs) {
                ++entryCounter;
            }
            if (dl.entranceTimeMs < endTimeMs && dl.exitTimeMs > startTimeMs)
                ++intensityCounter;
        }
        double intensity = intensityCounter / lengthKm;
        System.out.println("a. The number of vehicles that passed the entry point recorder: " + entryCounter);
        System.out.println("b. The traffic intensity: " + intensity);
        System.out.println();
    }

    void exercise5() {
        System.out.println("Exercise 5.");
        DataLine theFastest = null;
        int maxSpeed = 0;
        for (DataLine dl : measurements) {
            int speed = dl.getSpeed(lengthKm);
            if (theFastest == null || speed > maxSpeed) {
                maxSpeed = speed;
                theFastest = dl;
            }
        }
        int overtakenCounter = 0;
        for (DataLine dl : measurements) {
            if (dl.entranceTimeMs < theFastest.entranceTimeMs && dl.exitTimeMs > theFastest.exitTimeMs)
                ++overtakenCounter;
        }
        System.out.println("The data of the vehicle with the highest speed are");
        System.out.println("license plate number: " + theFastest.license);
        System.out.printf("average speed: %d km/h%n", maxSpeed);
        System.out.printf("number of overtaken vehicles: %d%n%n", overtakenCounter);
    }

    void exercise6() {
        System.out.println("Exercise 6.");
        int speedingCounter = 0;
        for (DataLine dl : measurements) {
            int speed = dl.getSpeed(lengthKm);
            if (speed >= speedLimit)
                ++speedingCounter;
        }
        System.out.printf("%.2f%% percent of the vehicles were speeding.", 100.0 * speedingCounter / measurements.size());
    }

    public static void main(String[] args) {
        SectionSpeedControl ssp = new SectionSpeedControl(10, 90);
        try {
            ssp.exercise1(args[0]);
            ssp.exercise2();
            ssp.exercise3(9);
            ssp.exercise4();
            ssp.exercise5();
            ssp.exercise6();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
