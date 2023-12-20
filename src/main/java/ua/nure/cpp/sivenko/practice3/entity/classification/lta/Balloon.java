package ua.nure.cpp.sivenko.practice3.entity.classification.lta;

import ua.nure.cpp.sivenko.practice3.entity.classification.LtaAircraft;

import java.util.Objects;

public class Balloon extends LtaAircraft {
    private int envelopeVolume;
    private int basketCapacity;

    public Balloon() {}

    public Balloon(int envelopeVolume, int basketCapacity) {
        this.envelopeVolume = envelopeVolume;
        this.basketCapacity = basketCapacity;
    }

    public Balloon(String model, int weight, int gasCapacity, LiftingGas liftingGas, int envelopeVolume, int basketCapacity) {
        super(model, weight, gasCapacity, liftingGas);
        setEnvelopeVolume(envelopeVolume);
        setBasketCapacity(basketCapacity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Balloon balloon))
            return false;

        if (!super.equals(obj))
            return false;

        return envelopeVolume == balloon.envelopeVolume && basketCapacity == balloon.basketCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), envelopeVolume, basketCapacity);
    }

    @Override
    public String toString() {
        return "\n-Aircraft Type: " + this.getClass().getSimpleName() +
                "\nEnvelope Volume: " + envelopeVolume +
                "\nBasket Capacity: " + basketCapacity + "\n";
    }

    public int getEnvelopeVolume() {
        return envelopeVolume;
    }

    public int getBasketCapacity() {
        return basketCapacity;
    }

    public void setEnvelopeVolume(int envelopeVolume) {
        if (envelopeVolume <= 0)
            throw new IllegalArgumentException("Envelope volume cannot be 0 or negative");
        this.envelopeVolume = envelopeVolume;
    }

    public void setBasketCapacity(int basketCapacity) {
        if (basketCapacity <= 0)
            throw new IllegalArgumentException("Basket capacity cannot be 0 or negative");
        this.basketCapacity = basketCapacity;
    }
}
