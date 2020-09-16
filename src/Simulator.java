import constructs.Station;
import generators.BusGenerator;
import generators.PassengerGenerator;

import java.io.IOException;

public class Simulator {
    public static void main(String[] args) {
        // greeting message
        System.out.println("\n" + "\u001B[33m" + "   ********** * **********" + "\u001B[0m");
        System.out.println("\u001B[32m"+ "Welcome to Senate Bus Simulator" + "\u001B[0m");
        System.out.println("\u001B[33m" + "   ********** * **********\n" + "\u001B[0m");
        System.out.println("\u001B[31m[INFO]\u001B[0m" + " Press Enter to exit ...\n");

        // create and initialize global variables
        int busCapacity = 50;
        float busArrivalMeanTime = 6f * 20 * 1000;
        float passengerArrivalMeanTime = 3f * 1000;

        // create the bus station
        Station station1 = new Station(1, busCapacity);

        // create passenger and bus generators
        PassengerGenerator pg = new PassengerGenerator(passengerArrivalMeanTime, station1);
        pg.start();

        BusGenerator bg = new BusGenerator(busArrivalMeanTime, busCapacity, station1);
        bg.start();

        // listen on user pressing enter to stop the program
        while (true) {
            try {
                int userPressKey = System.in.read();
                if(userPressKey != 0) {
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
