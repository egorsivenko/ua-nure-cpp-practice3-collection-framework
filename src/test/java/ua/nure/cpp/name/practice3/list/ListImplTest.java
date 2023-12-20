package ua.nure.cpp.name.practice3.list;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static ua.nure.cpp.name.practice3.list.Util.*;

class ListImplTest {

    static void assertArrayEqualsIgnoreOrdering(Object[] expected, Object[] actual, String message) {
        assertEquals(expected.length, actual.length, message);
        List<Object> expectedList = List.of(expected);
        List<Object> actualList = List.of(actual);
        assertEquals(expectedList.containsAll(actualList), actualList.containsAll(expectedList), message);
    }

    @Test
    void testClear() {
        Object list = newObject(getListImplementorFqn());
        Object[] arr = initEntities();

        Arrays.stream(arr).forEach(e -> call(list, "addFirst", e));
        assertEquals(arr.length, call(list, "size"));
        call(list, "clear");
        assertEquals(0, call(list, "size"));

        Arrays.stream(arr).forEach(e -> call(list, "addLast", e));
        assertEquals(arr.length, call(list, "size"));
        call(list, "clear");
        assertEquals(0, call(list, "size"));
    }

    @Test
    void testAddFirstAddLastClearToArray() {
        Object list = newObject(getListImplementorFqn());
        Object[] arr = initEntities();
        final AtomicInteger exactedSize = new AtomicInteger();
        Arrays.stream(arr).forEach(e -> {
            call(list, "addLast", e);
            assertEquals(exactedSize.incrementAndGet(), call(list, "size"),
                    "Size must be changed if an element is added to the list");
            assertEquals(e, call(list, "getLast"));
            call(list, "addFirst", e);
            assertEquals(exactedSize.incrementAndGet(), call(list, "size"),
                    "Size must be changed if an element is added to the list");
            assertEquals(e, call(list, "getFirst"));
        });
        Object[] clone = initEntities();
        Collections.reverse(Arrays.asList(clone));
        clone = Arrays.copyOf(clone, clone.length * 2);
        System.arraycopy(arr, 0, clone, arr.length, arr.length);
        assertArrayEquals(clone, (Object[]) call(list, "toArray"),
                "Elements must be stored in addition order");
    }

    @Test
    void testRemoveFirst() {
        Object list = newObject(getListImplementorFqn());
        Object[] arr = initEntities();
        final AtomicInteger index = new AtomicInteger();
        final AtomicInteger size = new AtomicInteger(arr.length);
        Arrays.stream(arr).forEach(e -> call(list, "addFirst", e));
        Collections.reverse(Arrays.asList(arr));
        Arrays.stream(arr).forEach(e -> {
            call(list, "removeFirst");
            Object[] expected = new Object[size.decrementAndGet()];
            System.arraycopy(arr, index.incrementAndGet(), expected, 0, expected.length);
            assertArrayEquals(expected, (Object[]) call(list, "toArray"),
                    "Elements must be stored in addition order");
        });
    }

    @Test
    void testRemoveLast() {
        Object list = newObject(getListImplementorFqn());
        Object[] arr = initEntities();
        Arrays.stream(arr).forEach(e -> call(list, "addLast", e));
        final AtomicInteger size = new AtomicInteger(arr.length);
        Arrays.stream(arr).forEach(e -> {
            call(list, "removeLast");
            Object[] expected = new Object[size.decrementAndGet()];
            System.arraycopy(arr, 0, expected, 0, size.get());
            Object[] actual = (Object[]) call(list, "toArray");
            assertArrayEquals(expected, actual,
                    "Elements must be stored in addition order");
        });
    }

    @Test
    void testGet() {
        Object list = newObject(getListImplementorFqn());
        Object[] arr = initEntities();
        Arrays.stream(arr).forEach(e -> Util.call(list, "addLast", e));
        arr = initEntities();
        Arrays.stream(arr).forEach(e -> assertEquals(e, call(list, "get", e),
                    "The equals method must be used to compare objects"));
    }

    @Test
    void testRemove() {
        Object list = newObject(getListImplementorFqn());
        Object[] arr = initEntities();
        Arrays.stream(arr).forEach(e -> call(list, "addLast", e));
        Arrays.stream(arr).forEach(e -> call(list, "addFirst", e));
        AtomicInteger size = new AtomicInteger(arr.length * 2);
        arr = initEntities();
        Arrays.stream(arr).forEach(e -> {
            assertTrue((Boolean) call(list, "remove", e));
            assertEquals(size.decrementAndGet(), call(list, "size"),
                    "The equals method must be used to compare objects");
        });
    }
}