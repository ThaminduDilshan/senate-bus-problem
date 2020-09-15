public class Passenger {
    private int passengerID;

    public Passenger(int passengerID) {
        this.passengerID = passengerID;
    }

    public void arrive(Station station) {
        System.out.println("Passenger #" + passengerID + " arrived.");

        // block if a bus is already boarding
        station.getBoardLock().acquireUninterruptibly();
        station.getBoardLock().release();

        // move into the station
        station.incrementPassengerCount();

        // try to board to the next bus. Blocked if more than busCapacity number of passengers are waiting
        station.getBoardNextBusSemaphore().acquireUninterruptibly();

        // wait for the bus to arrive
        System.out.println("Passenger #" + passengerID + " waiting...");
        while(!station.isBusArrived()) {
            // waiting
        }

        // board to the bus
        boardBus(station);
    }

    public void boardBus(Station station) {
        Bus tempBus = station.getCurrentBus();
        System.out.println("Passenger #" + passengerID + " boarded to the bus #" + tempBus.getBusID());
        tempBus.incrementPassengerCount();
        station.decrementPassengerCount();
    }

}
