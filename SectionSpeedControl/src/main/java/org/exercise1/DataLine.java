package org.exercise1;

public class DataLine {
    final String license;
    final int entranceTimeMs;
    final int exitTimeMs;

    public DataLine(String line) {
        String[] splitLine = line.split(" ");
        license = splitLine[0];
        int entranceHour = Integer.parseInt(splitLine[1]);
        int entranceMinute = Integer.parseInt(splitLine[2]);
        int entranceSecond = Integer.parseInt(splitLine[3]);
        int entranceMs = Integer.parseInt(splitLine[4]);
        entranceTimeMs = entranceMs + 1000 * (entranceSecond + 60 * (entranceMinute + 60 * entranceHour));
        int exitHour = Integer.parseInt(splitLine[5]);
        int exitMinute = Integer.parseInt(splitLine[6]);
        int exitSecond = Integer.parseInt(splitLine[7]);
        int exitMs = Integer.parseInt(splitLine[8]);
        exitTimeMs = exitMs + 1000 * (exitSecond + 60 * (exitMinute + 60 * exitHour));
    }

    public int getSpeed(double distance) {
        int elapsedMs = exitTimeMs - entranceTimeMs;
        return  (int) (distance * 3600000 / elapsedMs);
    }

}
