package ua.nure.cpp.sivenko.practice3.entity.classification.lta;

import ua.nure.cpp.sivenko.practice3.entity.classification.LtaAircraft;

import java.util.Objects;

public class Airship extends LtaAircraft {
    private int gasBags;
    private int passengerCapacity;

    public Airship() {}

    public Airship(int gasBags, int passengerCapacity) {
        this.gasBags = gasBags;
        this.passengerCapacity = passengerCapacity;
    }

    public Airship(String model, int weight, int gasCapacity, LiftingGas liftingGas, int gasBags, int passengerCapacity) {
        super(model, weight, gasCapacity, liftingGas);
        setGasBags(gasBags);
        setPassengerCapacity(passengerCapacity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Airship airship))
            return false;

        if (!super.equals(obj))
            return false;

        return gasBags == airship.gasBags && passengerCapacity == airship.passengerCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gasBags, passengerCapacity);
    }

    @Override
    public String toString() {
        return "\n-Aircraft Type: " + this.getClass().getSimpleName() +
                "\nGas Bags: " + gasBags +
                "\nPassenger Capacity: " + passengerCapacity + "\n";
    }

    public int getGasBags() {
        return gasBags;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setGasBags(int gasBags) {
        if (gasBags <= 0)
            throw new IllegalArgumentException("Gas bags amount cannot be 0 or negative");
        this.gasBags = gasBags;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        if (passengerCapacity <= 0)
            throw new IllegalArgumentException("Passenger capacity cannot be 0 or negative");
        this.passengerCapacity = passengerCapacity;
    }
}
