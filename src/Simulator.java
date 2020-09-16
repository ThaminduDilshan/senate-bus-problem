import constructs.Station;
import generators.BusGenerator;
import generators.PassengerGenerator;

public class Simulator {
    public static void main(String[] args) {
        // create and initialize global variables
        int busCapacity = 50;
        float busArrivalMeanTime = 6f * 20 * 1000;
        float passengerArrivalMeanTime = 3f * 1000;

        // create the bus station
        Station station1 = new Station(1, busCapacity);

        // create passenger and bus generators
        PassengerGenerator pg = new PassengerGenerator(passengerArrivalMeanTime, station1);
        pg.start();

        BusGenerator bg = new BusGenerator(busArrivalMeanTime, busCapacity, station1);
        bg.start();
    }
}
