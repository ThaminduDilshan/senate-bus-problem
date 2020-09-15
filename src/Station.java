import java.util.concurrent.Semaphore;

public class Station {
    private int stationID;
    private Semaphore boardLock;        // block new passengers boarding, if a bus is already boarding
    private Semaphore boardNextBusSemaphore;

    private boolean busArrived;
    private Semaphore busArrivedMutex;

    private Semaphore passengerLock;        // passengerLock is used to modify the passengerCount
    private int passengerCount;
    private Bus currentBus;

    public Station(int stationID, int busCapacity) {
        this.stationID = stationID;
        boardLock = new Semaphore(1);
        boardNextBusSemaphore = new Semaphore(busCapacity, true);
        busArrived = false;
        busArrivedMutex = new Semaphore(1);
        passengerLock = new Semaphore(1);
        passengerCount = 0;
        currentBus = null;
    }

    public Semaphore getBoardLock() {
        return boardLock;
    }

    public Semaphore getBoardNextBusSemaphore() {
        return boardNextBusSemaphore;
    }

    public boolean isBusArrived() {
        return busArrived;
    }

    public void setBusArrived(boolean busArrived) {
        busArrivedMutex.acquireUninterruptibly();
        this.busArrived = busArrived;
        busArrivedMutex.release();
    }

    public Bus getCurrentBus() {
        return currentBus;
    }

    public void setCurrentBus(Bus currentBus) {
        this.currentBus = currentBus;
    }

    public int getPassengerCount() {
        passengerLock.acquireUninterruptibly();
        int temp = passengerCount;
        passengerLock.release();
        return temp;
    }

    public void decrementPassengerCount() {
        passengerLock.acquireUninterruptibly();
        passengerCount -= 1;
        passengerLock.release();
    }

    public void incrementPassengerCount() {
        passengerLock.acquireUninterruptibly();
        passengerCount += 1;
        passengerLock.release();
    }
}
