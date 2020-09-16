package generators;

import constructs.Passenger;
import constructs.Station;

import java.util.Random;

public class PassengerGenerator extends Thread {
    private float arrivalMeanTime;
    private static Random random;
    private Station station;

    public PassengerGenerator(float arrivalMeanTime, Station station) {
        this.arrivalMeanTime = arrivalMeanTime;
        this.station = station;
        random = new Random();
    }

    @Override
    public void run() {
        int currentPassengerID = 1;

        while (true) {
            // create and start a new passenger thread
            Passenger passenger = new Passenger(currentPassengerID, station);
            passenger.start();

            // wait for inter arrival time
            long nextPassengerWaitTime = calculateArrivalTime();

            try {
                Thread.sleep(nextPassengerWaitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentPassengerID += 1;
        }
    }

    // method to calculate exponentially distributed passenger inter arrival time
    private long calculateArrivalTime() {
        float lambda = 1 / arrivalMeanTime;
        return Math.round(Math.log(1 - random.nextFloat()) / -lambda);
    }

}
