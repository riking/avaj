package org.riking.home._42.avaj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Scenario {

    private int stepCount;
    private WeatherTower tower;

    public Scenario() {
        this.tower = new WeatherTower();
    }

    /**
     * Parse a scenario from the provided input reader.
     *
     * @param r Reader to read from
     * @throws InvalidScenarioException The scenario file contains a format error.
     */
    public void parse(Reader r) throws InvalidScenarioException {
        BufferedReader in = new BufferedReader(r);

        try {
            String countLine = in.readLine();
            if (countLine == null) {
                throw new InvalidScenarioException("Empty file");
            }
            try {
                this.stepCount = Integer.parseInt(countLine);
            } catch (NumberFormatException nfe) {
                throw new InvalidScenarioException("First line is not a valid integer", nfe);
            }
            if (this.stepCount <= 0) {
                throw new InvalidScenarioException("Weather change count must be positive, got " + this.stepCount);
            }

            String line = in.readLine();
            int lineNumber = 1;
            for (; line != null; line = in.readLine()) {
                lineNumber += 1;

                String[] split = line.split(" ", 5);
                if (split.length != 5) {
                    throw new InvalidScenarioException("Short line: " + line);
                }

                int longitude = throwParse("longitude", lineNumber, split[2]);
                int latitude = throwParse("latitude", lineNumber, split[3]);
                int height = throwParse("height", lineNumber, split[4]);

                Flyable flyable;

                try {
                    flyable = AircraftFactory.newAircraft(split[0], split[1], longitude, latitude, height);
                } catch (IllegalArgumentException e) {
                    throw new InvalidScenarioException(e);
                }

                flyable.registerTower(this.tower);
            }
            // EOF, done
        } catch (IOException e) {
            throw new InvalidScenarioException(e);
        } finally {
            try {
                in.close();
            } catch (IOException nested) {
                // don't care
            }
        }
    }

    public void run() {
        for (int i = 0; i < stepCount; i++) {
            this.tower.changeWeather();
        }
    }

    private static int throwParse(String inputType, int lineNum, String number) throws InvalidScenarioException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            throw new InvalidScenarioException("Invalid " + inputType + " on line " + lineNum + ": " + number, nfe);
        }
    }

    public static class InvalidScenarioException extends Exception {
        public InvalidScenarioException(String message) {
            super(message);
        }

        public InvalidScenarioException(Exception cause) {
            super(cause);
        }

        public InvalidScenarioException(String message, Exception cause) {
            super(message, cause);
        }
    }
}
