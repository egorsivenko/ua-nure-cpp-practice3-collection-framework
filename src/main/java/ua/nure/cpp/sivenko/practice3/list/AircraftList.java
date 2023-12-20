package ua.nure.cpp.sivenko.practice3.list;

import ua.nure.cpp.sivenko.practice3.entity.Aircraft;

public interface AircraftList extends Container {
    void addFirst(Aircraft element);

    void addLast(Aircraft element);

    void add(int index, Aircraft element);

    void addAll(Aircraft... elements);

    void removeFirst();

    void removeLast();

    void remove(int index);

    boolean remove(Aircraft element);

    Aircraft getFirst();

    Aircraft getLast();

    Aircraft get(int index);

    Aircraft get(Aircraft element);

    int indexOf(Aircraft element);

    boolean contains(Aircraft element);
}
