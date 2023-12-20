package ua.nure.cpp.sivenko.practice3.entity.classification;

import ua.nure.cpp.sivenko.practice3.entity.Aircraft;

import java.util.Objects;

public abstract class HtaAircraft extends Aircraft {
    protected int wingspan;
    protected PropulsionType propulsionType = PropulsionType.UNDEFINED;

    public HtaAircraft() {}

    public HtaAircraft(String model, int weight, int wingspan, PropulsionType propulsionType) {
        super(model, weight);
        setWingspan(wingspan);
        setPropulsionType(propulsionType);
    }

    public enum PropulsionType {
        TURBOFAN("Turbofan"),
        TURBOJET("Turbojet"),
        TURBOPROP("Turboprop"),
        TURBOSHAFT("Turboshaft"),
        RAMJET("Ramjet"),
        UNDEFINED("Undefined");

        private final String label;

        PropulsionType(String label) {
            this.label = label;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof HtaAircraft htaAircraft))
            return false;

        if (!super.equals(obj))
            return false;

        return wingspan == htaAircraft.wingspan && propulsionType == htaAircraft.propulsionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wingspan, propulsionType);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nWingspan: " + wingspan +
                "\nPropulsion Type: " + propulsionType.label;
    }

    public int getWingspan() {
        return wingspan;
    }

    public PropulsionType getPropulsionType() {
        return propulsionType;
    }

    public void setWingspan(int wingspan) {
        if (wingspan < 0)
            throw new IllegalArgumentException("Wingspan cannot be negative");
        this.wingspan = wingspan;
    }

    public void setPropulsionType(PropulsionType propulsionType) {
        if (propulsionType == null)
            throw new IllegalArgumentException("Propulsion type cannot be null");
        this.propulsionType = propulsionType;
    }
}
