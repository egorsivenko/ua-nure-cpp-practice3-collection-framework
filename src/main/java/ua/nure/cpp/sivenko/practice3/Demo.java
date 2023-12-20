package ua.nure.cpp.sivenko.practice3;

import ua.nure.cpp.sivenko.practice3.list.AircraftListImpl;

import ua.nure.cpp.sivenko.practice3.entity.Aircraft;
import ua.nure.cpp.sivenko.practice3.entity.classification.hta.*;
import ua.nure.cpp.sivenko.practice3.entity.classification.lta.*;

import static ua.nure.cpp.sivenko.practice3.entity.classification.hta.Airplane.WingType;

import java.util.Iterator;

public class Demo {
    public static void main(String[] args) {
        var aircraftList = new AircraftListImpl();
        aircraftList.addAll(new Airplane(4, WingType.TAPERED_STRAIGHT),
                new Balloon(300, 650),
                new Rocket(700, 2600),
                new Airplane(7, WingType.ELLIPTICAL));

        System.out.println("Basic state of list:");
        System.out.println(aircraftList);

        System.out.println("\n\nTwo elements added:");
        aircraftList.add(0, new Rotorcraft(20, 1000));
        aircraftList.add(3, new PoweredLift(51, 74));
        System.out.println(aircraftList);

        System.out.println("\n\nSome removals:");
        aircraftList.remove(3);
        boolean wasRemoved = aircraftList.remove(new Airplane(7, WingType.ELLIPTICAL));
        System.out.println("Airplane was removed: " + wasRemoved);
        System.out.println(aircraftList);

        System.out.println("\nSecond element of a list:");
        System.out.println(aircraftList.get(1));

        System.out.println("\nGetting a Rocket:");
        System.out.println(aircraftList.get(new Rocket(700, 2600)));

        System.out.println("\nSecond elements was replaced with Balloon: ");
        System.out.println(aircraftList.set(1, new Balloon(400, 580)));
        System.out.println(aircraftList);

        System.out.println("Contains null: " + aircraftList.contains(null));
        System.out.println("Contains Balloon: " + aircraftList.contains(new Balloon(300, 650)));


        System.out.println("\n\nIterator with For-each loop:");
        for (var aircraft : aircraftList)
            System.out.println(aircraft);

        System.out.println("\nIterator with While loop:");
        Iterator<Aircraft> iterator = aircraftList.iterator();
        while (iterator.hasNext()) {
            var aircraft = iterator.next();
            System.out.println(aircraft);
        }

        System.out.println("\nIterator remove(4 handled exceptions):\n");
        iterator = aircraftList.iterator();
        while (iterator.hasNext()) {
            var aircraft = iterator.next();
            iterator.remove();
            try {
                iterator.remove();
            }
            catch (IllegalStateException ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
        }
    }
}
