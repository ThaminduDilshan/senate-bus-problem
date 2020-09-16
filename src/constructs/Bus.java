package constructs;

import java.util.concurrent.Semaphore;

public class Bus extends Thread {
    private int busID;
    private int capacity;
    private int passengerCount;
    private Semaphore passengerCountMutex;
    private Station station;

    public Bus(int busID, int capacity) {
        this.busID = busID;
        this.capacity = capacity;
        this.passengerCount = 0;
        passengerCountMutex = new Semaphore(1);
    }

    @Override
    public void run() {
        if(station == null) {
            System.out.println("\u001B[31m[BUS]\u001B[0m No station found for the bus #" + busID);
        } else {
            arrive(station);
        }
    }

    public void arrive(Station station) {
        station.getBoardMutex().acquireUninterruptibly();

        System.out.println("\u001B[34m[BUS]\u001B[0m Bus #" + busID + " arrived at the station !");
        station.setCurrentBus(this);

        if(station.getPassengerCount() != 0) {
            station.getBusMutex().release();
            station.getDoorLock().acquireUninterruptibly();
        }

        station.getBoardMutex().release();
        depart(station);

    }

    public void depart(Station station) {
        System.out.println("\u001B[32m[BUS]\u001B[0m Bus #" + busID + " departed with " + passengerCount + " Passengers !\n");
    }

    public int getBusID() {
        return busID;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void incrementPassengerCount() {
        passengerCountMutex.acquireUninterruptibly();
        if(passengerCount < capacity) {
            passengerCount += 1;
        }
        passengerCountMutex.release();
    }
}
