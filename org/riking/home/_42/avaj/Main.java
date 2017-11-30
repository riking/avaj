package org.riking.home._42.avaj;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java org.riking.home._42.avaj.Main scenario.txt");
            System.exit(2);
        }

        try (Logger logger = Logger.setupFile("simulation.txt")) {

            Scenario scenario = new Scenario();

            try (FileReader fr = new FileReader(args[0])) {
                scenario.parse(fr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println("The first argument must be the scenario file.");
                System.exit(1);
            } catch (Scenario.InvalidScenarioException | IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            scenario.run();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Could not initialize logger; please fix file permissions and run the program again.");
            System.exit(2);
        }

    }
}
