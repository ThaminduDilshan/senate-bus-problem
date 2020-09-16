package generators;

import constructs.Bus;
import constructs.Station;

import java.util.Random;

public class BusGenerator extends Thread {
    private float arrivalMeanTime;
    private static Random random;
    private int busCapacity;
    private Station station;

    public BusGenerator(float arrivalMeanTime, int busCapacity, Station station) {
        this.arrivalMeanTime = arrivalMeanTime;
        this.busCapacity = busCapacity;
        this.station = station;
        random = new Random();
    }

    @Override
    public void run() {
        int currentBusID = 1;

        while(!Thread.currentThread().isInterrupted()) {
            // create and start a new bus thread
            Bus bus = new Bus(currentBusID, busCapacity);
            bus.setStation(station);
            bus.start();

            // wait for inter arrival time
            long nextBusWaitTime = calculateArrivalTime();
            if(nextBusWaitTime > 60000) {
                System.out.println("********** Next bus will be in " + nextBusWaitTime/(1000*60) + " minutes **********");
            } else {
                System.out.println("********** Next bus will be in " + nextBusWaitTime/1000 + " seconds **********");
            }

            try {
                Thread.sleep(nextBusWaitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentBusID += 1;
        }
    }

    // method to calculate exponentially distributed bus inter arrival time
    private long calculateArrivalTime() {
        float lambda = 1 / arrivalMeanTime;
        return Math.round(Math.log(1 - random.nextFloat()) / -lambda);
    }

}
