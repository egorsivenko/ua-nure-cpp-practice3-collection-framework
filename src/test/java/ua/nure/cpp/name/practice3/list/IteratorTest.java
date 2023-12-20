package ua.nure.cpp.name.practice3.list;

import static org.junit.jupiter.api.Assertions.*;
import static ua.nure.cpp.name.practice3.list.Util.call;
import static ua.nure.cpp.name.practice3.list.Util.getListImplementorFqn;
import static ua.nure.cpp.name.practice3.list.Util.initEntities;
import static ua.nure.cpp.name.practice3.list.Util.newObject;

import java.util.*;

import org.junit.jupiter.api.Test;

class IteratorTest {
    
    @Test
    void testListIsIterble() {
    	Object list = newObject(getListImplementorFqn());
    	assertTrue(list instanceof Iterable);
    }

    @Test
    void testIterator() {
        Object actual = newObject(getListImplementorFqn());
        Object[] arr = initEntities();
        Arrays.stream(arr).forEach(e -> {
            call(actual, "addLast", e);
            call(actual, "addFirst", e);
        });
        assertTrue(actual instanceof Iterable,
                "Container must extends 'java.lang.Iterable' and your implementation must must implements it.");

        Object[] data = (Object[]) call(actual, "toArray");
        List<Object> expected = new ArrayList<>(Arrays.asList(data));

        Iterator<?> eit = expected.iterator();
        @SuppressWarnings("unchecked")
		Iterator<?> ait = ((Iterable<Object>) actual).iterator();
        assertTrue(ait.hasNext(), 
        		"Must return true if there is at list one element to iterate");
        Object expectedNext = eit.next();
        Object actualNext = ait.next();
        assertEquals(eit.next(), ait.next(), 
        		"Must iterate from first to last elements");
        
        expectedNext = eit.next();
        actualNext = ait.next();
		assertEquals(expectedNext, actualNext, 
        		"Must iterate from first to last, expected = '" + expectedNext
        		+ "', actual = '" + actualNext + "'");

		eit.remove();
        ait.remove();
		assertArrayEquals(expected.toArray(), (Object[]) call(actual, "toArray"),
        		"Iterator#remove must remove last returned element");
        assertThrows(IllegalStateException.class, ait::remove,
        		"Iterator#remove must throw an IllegalStateException "
        		+ "if it is called twice without intermediate next().");

        while (ait.hasNext()) {
        	expectedNext = eit.next();
        	actualNext = ait.next();
        	assertEquals(expectedNext, actualNext,
        			"Must itarate from first to last expected = '" + expectedNext
        			+ "', actual = '" + actualNext + "'");
		}
        ait.remove();
        eit.remove();
        assertArrayEquals(expected.toArray(), (Object[]) call(actual, "toArray"),
        		"Iterator#remove must remove last returned element");
        assertThrows(NoSuchElementException.class, ait::next,
        		"Iterator#next must throw an 'NoSuchElementException' "
        		+ "if there is no more elements for iterations.");
    }
}
