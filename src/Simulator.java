public class Simulator {
    public static void main(String[] args) {
        // create and initialize global variables
        int busCapacity = 2;

        // create the bus station
        Station station1 = new Station(1, busCapacity);

        // Test execution //

        // create busses
        Bus bus1 = new Bus(1, busCapacity);
        bus1.setStation(station1);
        bus1.start();

        // create few sample passengers
        for (int i = 1; i <= 3; i++) {
            Passenger p = new Passenger(i, station1);
            p.start();
        }

    }
}
