public class Simulator {
    public static void main(String[] args) {
        // create and initialize global variables
        int busCapacity = 50;

        // create the bus station
        Station station1 = new Station(1, busCapacity);

        // create busses
        Bus bus1 = new Bus(1, busCapacity);

        // Test execution //

        // create few sample passengers
        for (int i = 1; i <= 100; i++) {
            Passenger p = new Passenger(i);
            p.arrive(station1);
        }

        bus1.arrive(station1);

    }
}
