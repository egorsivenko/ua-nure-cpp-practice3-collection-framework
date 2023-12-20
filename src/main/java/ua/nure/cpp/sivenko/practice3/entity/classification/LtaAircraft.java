package ua.nure.cpp.sivenko.practice3.entity.classification;

import ua.nure.cpp.sivenko.practice3.entity.Aircraft;

import java.util.Objects;

public abstract class LtaAircraft extends Aircraft {
    protected int gasCapacity;
    protected LiftingGas liftingGas = LiftingGas.UNDEFINED;

    public LtaAircraft() {}

    public LtaAircraft(String model, int weight, int gasCapacity, LiftingGas liftingGas) {
        super(model, weight);
        setGasCapacity(gasCapacity);
        setLiftingGas(liftingGas);
    }

    public enum LiftingGas {
        HYDROGEN("Hydrogen"),
        HELIUM("Helium"),
        COAL_GAS("Coal Gas"),
        AMMONIA("Ammonia"),
        METHANE("Methane"),
        UNDEFINED("Undefined");

        private final String label;

        LiftingGas(String label) {
            this.label = label;
        }
    }

    @Override
    public int compareTo(Aircraft other) {
        int c = super.compareTo(other);

        if (c == 0 && other instanceof LtaAircraft otherLtaAircraft) {
            return gasCapacity - otherLtaAircraft.gasCapacity;
        }
        return c;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof LtaAircraft ltaAircraft))
            return false;

        if (!super.equals(obj))
            return false;

        return gasCapacity == ltaAircraft.gasCapacity && liftingGas == ltaAircraft.liftingGas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gasCapacity, liftingGas);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nGas Capacity: " + gasCapacity +
                "\nLiftingGas: " + liftingGas.label;
    }

    public int getGasCapacity() {
        return gasCapacity;
    }

    public LiftingGas getLiftingGas() {
        return liftingGas;
    }

    public void setGasCapacity(int gasCapacity) {
        if (gasCapacity <= 0)
            throw new IllegalArgumentException("Gas capacity cannot be 0 or negative");
        this.gasCapacity = gasCapacity;
    }

    public void setLiftingGas(LiftingGas liftingGas) {
        if (liftingGas == null)
            throw new IllegalArgumentException("Lifting gas cannot be null");
        this.liftingGas = liftingGas;
    }
}
