package ua.nure.cpp.sivenko.practice3.entity.classification.hta;

import ua.nure.cpp.sivenko.practice3.entity.classification.HtaAircraft;

import java.util.Objects;

public class Rocket extends HtaAircraft {
    private int thrustPower;
    private int maxAltitude;

    public Rocket() {}

    public Rocket(int thrustPower, int maxAltitude) {
        this.thrustPower = thrustPower;
        this.maxAltitude = maxAltitude;
    }

    public Rocket(String model, int weight, int wingspan, PropulsionType propulsionType, int thrustPower, int maxAltitude) {
        super(model, weight, wingspan, propulsionType);
        setThrustPower(thrustPower);
        setMaxAltitude(maxAltitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Rocket rocket))
            return false;

        if (!super.equals(obj))
            return false;

        return thrustPower == rocket.thrustPower && maxAltitude == rocket.maxAltitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), thrustPower, maxAltitude);
    }

    @Override
    public String toString() {
        return "\n-Aircraft Type: " + this.getClass().getSimpleName() +
                "\nThrust Power: " + thrustPower +
                "\nMax Altitude: " + maxAltitude + "\n";
    }

    public int getThrustPower() {
        return thrustPower;
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setThrustPower(int thrustPower) {
        if (thrustPower <= 0)
            throw new IllegalArgumentException("Thrust power cannot be 0 or negative");
        this.thrustPower = thrustPower;
    }

    public void setMaxAltitude(int maxAltitude) {
        if (maxAltitude <= 0)
            throw new IllegalArgumentException("Max altitude cannot be 0 or negative");
        this.maxAltitude = maxAltitude;
    }
}
