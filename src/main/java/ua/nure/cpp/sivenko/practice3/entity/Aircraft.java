package ua.nure.cpp.sivenko.practice3.entity;

import java.util.Objects;

public abstract class Aircraft implements Comparable<Aircraft> {
    protected String model = "Unknown";
    protected int weight;

    public Aircraft() {}

    public Aircraft(String model, int weight) {
        setModel(model);
        setWeight(weight);
    }

    @Override
    public int compareTo(Aircraft other) {
        return weight - other.weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Aircraft aircraft))
            return false;

        return weight == aircraft.weight && Objects.equals(model, aircraft.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, weight);
    }

    @Override
    public String toString() {
        return "\nModel: " + model +
                "\nWeight: " + weight;
    }

    public String getModel() {
        return model;
    }

    public int getWeight() {
        return weight;
    }

    public void setModel(String model) {
        if (model.isBlank())
            throw new IllegalArgumentException("Model name cannot be empty");
        this.model = model;
    }

    public void setWeight(int weight) {
        if (weight <= 0)
            throw new IllegalArgumentException("Weight cannot be 0 or negative");
        this.weight = weight;
    }
}
