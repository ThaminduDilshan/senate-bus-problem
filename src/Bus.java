import java.util.concurrent.Semaphore;

public class Bus {
    private int busID;
    private int capacity;
    private int passengerCount;
    private Semaphore passengerCountMutex;

    public Bus(int busID, int capacity) {
        this.busID = busID;
        this.capacity = capacity;
        this.passengerCount = 0;
        passengerCountMutex = new Semaphore(1);
    }

    public void arrive(Station station) {
        System.out.println("Bus #" + busID + " arrived at the station!");
        passengerCountMutex.acquireUninterruptibly();
        passengerCount = 0;
        passengerCountMutex.release();

        // block the boarding
        station.getBoardLock().acquireUninterruptibly();
        System.out.println("Board Lock acquired!");
        station.setCurrentBus(this);

        // depart if no passengers are at the station
        if(station.getPassengerCount() == 0) {
            depart(station);
        } else {
            // notify the bus has arrived
            station.setBusArrived(true);

            // depart if bus is full or no more passengers to get in
            while(true) {
                if(passengerCount == capacity || station.getPassengerCount() == 0) {
                    depart(station);
                    break;
                }
            }
        }

    }

    public void depart(Station station) {
        station.setCurrentBus(null);
        station.getBoardNextBusSemaphore().release(passengerCount);
        station.getBoardLock().release();
        System.out.println("Board Lock released!");
        System.out.println("Bus #" + busID + " departed with " + passengerCount + " Passengers!");
    }

    public int getBusID() {
        return busID;
    }

    public void incrementPassengerCount() {
        passengerCountMutex.acquireUninterruptibly();
        if(passengerCount < capacity) {
            passengerCount += 1;
        }
        passengerCountMutex.release();
    }
}
