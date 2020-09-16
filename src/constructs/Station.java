package constructs;

import java.util.concurrent.Semaphore;

public class Station {
    private int stationID;
    private Semaphore boardNextBusSemaphore;        // passengers for the next bus
    private Semaphore doorLock;        // keep bus waiting until all passengers boarded
    private Semaphore busMutex;         // keep passengers waiting until a bus arrives
    private Semaphore boardMutex;       // block new passengers entering waiting area when a bus is boarded
    private static int passengerCount;
    private Semaphore passengerMutex;       // mutex for the variable passenger count

    private Bus currentBus;

    public Station(int stationID, int busCapacity) {
        this.stationID = stationID;
        boardNextBusSemaphore = new Semaphore(busCapacity, true);
        busMutex = new Semaphore(0, true);
        boardMutex = new Semaphore(1, true);
        passengerCount = 0;
        passengerMutex = new Semaphore(1);
        doorLock = new Semaphore(0, true);
        currentBus = null;
    }

    public Semaphore getBoardNextBusSemaphore() {
        return boardNextBusSemaphore;
    }

    public Semaphore getBusMutex() {
        return busMutex;
    }

    public Semaphore getBoardMutex() {
        return boardMutex;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void decrementPassengerCount() {
        passengerMutex.acquireUninterruptibly();
        passengerCount -= 1;
        passengerMutex.release();
    }

    public void incrementPassengerCount() {
        passengerMutex.acquireUninterruptibly();
        passengerCount += 1;
        passengerMutex.release();
    }

    public Semaphore getDoorLock() {
        return doorLock;
    }

    public Bus getCurrentBus() {
        return currentBus;
    }

    public void setCurrentBus(Bus currentBus) {
        this.currentBus = currentBus;
    }
}
