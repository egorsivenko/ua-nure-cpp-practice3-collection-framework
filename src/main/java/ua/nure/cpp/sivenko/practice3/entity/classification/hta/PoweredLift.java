package ua.nure.cpp.sivenko.practice3.entity.classification.hta;

import ua.nure.cpp.sivenko.practice3.entity.classification.HtaAircraft;

import java.util.Objects;

public class PoweredLift extends HtaAircraft {
    private int verticalTakeoffPower;
    private int shortTakeoffDistance;

    public PoweredLift() {}

    public PoweredLift(int verticalTakeoffPower, int shortTakeoffDistance) {
        this.verticalTakeoffPower = verticalTakeoffPower;
        this.shortTakeoffDistance = shortTakeoffDistance;
    }

    public PoweredLift(String model, int weight, int wingspan, PropulsionType propulsionType, int verticalTakeoffPower, int shortTakeoffDistance) {
        super(model, weight, wingspan, propulsionType);
        setVerticalTakeoffPower(verticalTakeoffPower);
        setShortTakeoffDistance(shortTakeoffDistance);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof PoweredLift poweredLift))
            return false;

        if (!super.equals(obj))
            return false;

        return verticalTakeoffPower == poweredLift.verticalTakeoffPower && shortTakeoffDistance == poweredLift.shortTakeoffDistance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), verticalTakeoffPower, shortTakeoffDistance);
    }

    @Override
    public String toString() {
        return "\n-Aircraft Type: " + this.getClass().getSimpleName() +
                "\nVertical Takeoff Power: " + verticalTakeoffPower +
                "\nShort Takeoff Distance: " + shortTakeoffDistance + "\n";
    }

    public int getVerticalTakeoffPower() {
        return verticalTakeoffPower;
    }

    public int getShortTakeoffDistance() {
        return shortTakeoffDistance;
    }

    public void setVerticalTakeoffPower(int verticalTakeoffPower) {
        if (verticalTakeoffPower <= 0)
            throw new IllegalArgumentException("Vertical take off power cannot be 0 or negative");
        this.verticalTakeoffPower = verticalTakeoffPower;
    }

    public void setShortTakeoffDistance(int shortTakeoffDistance) {
        if (shortTakeoffDistance <= 0)
            throw new IllegalArgumentException("Short take off distance cannot be 0 or negative");
        this.shortTakeoffDistance = shortTakeoffDistance;
    }
}
