package constructs;

public class Passenger extends Thread {
    private int passengerID;
    private Station station;

    public Passenger(int passengerID, Station station) {
        this.passengerID = passengerID;
        this.station = station;
    }

    @Override
    public void run() {
        arrive(station);
    }

    public void arrive(Station station) {
        System.out.println("[PASSENGER] Passenger #" + passengerID + " arrived.");

        // will be blocked if a bus is boarded
        station.getBoardMutex().acquireUninterruptibly();
        station.getBoardMutex().release();

        // try to board to the next bus. Blocked if more than busCapacity number of passengers are waiting
        station.getBoardNextBusSemaphore().acquireUninterruptibly();

        // move into the station
        station.incrementPassengerCount();

        // wait for the bus to arrive
        // System.out.println("[PASSENGER] Passenger #" + passengerID + " waiting for the bus...");
        station.getBusMutex().acquireUninterruptibly();

        // board to the bus
        boardBus(station);

        // last passenger to board will release the door lock
        if(station.getPassengerCount() == 0) {
            station.getDoorLock().release();
        } else {
            station.getBusMutex().release();
        }
    }

    public void boardBus(Station station) {
        Bus tempBus = station.getCurrentBus();
        station.decrementPassengerCount();
        tempBus.incrementPassengerCount();
        System.out.println("[PASSENGER] Passenger #" + passengerID + " boarded to bus #" + tempBus.getBusID());
    }

}
