package ua.nure.cpp.sivenko.practice3.entity.classification.hta;

import ua.nure.cpp.sivenko.practice3.entity.classification.HtaAircraft;

import java.util.Objects;

public class Airplane extends HtaAircraft {
    private int numberOfEngines;
    private WingType wingType = WingType.UNDEFINED;

    public Airplane() {}

    public Airplane(int numberOfEngines, WingType wingType) {
        this.numberOfEngines = numberOfEngines;
        this.wingType = wingType;
    }

    public Airplane(String model, int weight, int wingspan, PropulsionType propulsionType, int numberOfEngines, WingType wingType) {
        super(model, weight, wingspan, propulsionType);
        setNumberOfEngines(numberOfEngines);
        setWingType(wingType);
    }

    public enum WingType {
        RECTANGULAR("Rectangular"),
        TAPERED_STRAIGHT("Tapered Straight"),
        ELLIPTICAL("Elliptical"),
        SWEPT("Swept"),
        DELTA("Delta"),
        UNDEFINED("Undefined");

        private final String label;

        WingType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Airplane airplane))
            return false;

        if (!super.equals(obj))
            return false;

        return numberOfEngines == airplane.numberOfEngines && wingType == airplane.wingType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfEngines, wingType);
    }

    @Override
    public String toString() {
        return "\n-Aircraft Type: " + this.getClass().getSimpleName() +
                "\nNumber of Engines: " + numberOfEngines +
                "\nWing Type: " + wingType.label + "\n";
    }

    public int getNumberOfEngines() {
        return numberOfEngines;
    }

    public WingType getWingType() {
        return wingType;
    }

    public void setNumberOfEngines(int numberOfEngines) {
        if (numberOfEngines <= 0)
            throw new IllegalArgumentException("Number of engines cannot be 0 or negative");
        this.numberOfEngines = numberOfEngines;
    }

    public void setWingType(WingType wingType) {
        if (wingType == null)
            throw new IllegalArgumentException("Wing type cannot be null");
        this.wingType = wingType;
    }
}
