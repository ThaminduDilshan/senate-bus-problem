import java.util.concurrent.Semaphore;

public class Station {
    private int stationID;
    private Semaphore boardNextBusSemaphore;        // passengers for the next bus
    private Semaphore doorLock;        //
    private Semaphore busMutex;         // wait passengers until a bus arrives

    private int passengerCount;
    private Semaphore passengerMutex;       // block new passengers entering waiting area, if the bus arrived

    private Bus currentBus;

    public Station(int stationID, int busCapacity) {
        this.stationID = stationID;
        boardNextBusSemaphore = new Semaphore(busCapacity, true);

        busMutex = new Semaphore(0, true);

        passengerCount = 0;
        passengerMutex = new Semaphore(1);
//        passengerLock = new ReentrantReadWriteLock();
//        passengerReadLock = passengerLock.readLock();
//        passengerWriteLock = passengerLock.writeLock();

        doorLock = new Semaphore(0, true);
        currentBus = null;
    }

    public Semaphore getBoardNextBusSemaphore() {
        return boardNextBusSemaphore;
    }


    public Semaphore getBusMutex() {
        return busMutex;
    }

    public int getPassengerCount() {
//        passengerReadLock.lock();
//        int temp = passengerCount;
//        passengerReadLock.unlock();
//        return temp;
        return passengerCount;
    }

    public void decrementPassengerCount() {
//        passengerWriteLock.lock();
//        passengerMutex.acquireUninterruptibly();
        passengerCount -= 1;
//        passengerWriteLock.unlock();
//        passengerMutex.release();
    }

    public void incrementPassengerCount() {
//        passengerWriteLock.lock();
//        passengerMutex.acquireUninterruptibly();
        passengerCount += 1;
//        passengerWriteLock.unlock();
//        passengerMutex.release();
    }

    public Semaphore getPassengerMutex() {
        return passengerMutex;
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
