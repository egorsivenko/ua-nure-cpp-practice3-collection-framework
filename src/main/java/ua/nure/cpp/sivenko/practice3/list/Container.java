package ua.nure.cpp.sivenko.practice3.list;

import ua.nure.cpp.sivenko.practice3.entity.Aircraft;

import java.util.Iterator;

public interface Container extends Iterable<Aircraft> {
    void clear();

    int size();

    boolean isEmpty();

    String toString();

    Aircraft[] toArray();

    Iterator<Aircraft> iterator();
}
