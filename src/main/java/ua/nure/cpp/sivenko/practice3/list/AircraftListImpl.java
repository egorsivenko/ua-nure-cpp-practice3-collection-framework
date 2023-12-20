package ua.nure.cpp.sivenko.practice3.list;

import ua.nure.cpp.sivenko.practice3.entity.Aircraft;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AircraftListImpl implements AircraftList {
    private static class Node {
        private Aircraft value;
        private Node next;
        private Node previous;

        public Node(Aircraft value) {
            this.value = value;
        }
    }

    private Node first;
    private Node last;
    private int size;

    @Override
    public void addFirst(Aircraft element) {
        Node node = new Node(element);

        if (isEmpty())
            first = last = node;
        else {
            node.next = first;
            first.previous = node;
            first = node;
        }
        size++;
    }

    @Override
    public void addLast(Aircraft element) {
        Node node = new Node(element);

        if (isEmpty())
            first = last = node;
        else {
            last.next = node;
            node.previous = last;
            last = node;
        }
        size++;
    }

    @Override
    public void add(int index, Aircraft element) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        if (index == 0)
            addFirst(element);
        else if (index == size)
            addLast(element);
        else
            addMiddle(index, element);
    }

    @Override
    public void addAll(Aircraft... elements) {
        for (var element : elements)
            addLast(element);
    }

    private void addMiddle(int index, Aircraft element) {
        Node node = new Node(element);
        Node previous = getNode(index - 1);
        Node next = previous.next;

        previous.next = node;
        next.previous = node;
        node.next = next;
        node.previous = previous;
        size++;
    }

    @Override
    public void removeFirst() {
        checkIfEmpty();

        if (first == last)
            first = last = null;
        else {
            Node next = first.next;
            first.next = null;
            next.previous = null;
            first = next;
        }
        size--;
    }

    @Override
    public void removeLast() {
        checkIfEmpty();

        if (first == last)
            first = last = null;
        else {
            Node previous = last.previous;
            last.previous = null;
            previous.next = null;
            last = previous;
        }
        size--;
    }

    @Override
    public void remove(int index) {
        Node node = getNode(index);
        unlinkNode(node);
    }

    @Override
    public boolean remove(Aircraft element) {
        if (element == null) {
            for (Node node = first; node != null; node = node.next) {
                if (node.value == null) {
                    unlinkNode(node);
                    return true;
                }
            }
        } else {
            for (Node node = first; node != null; node = node.next) {
                if (element.equals(node.value)) {
                    unlinkNode(node);
                    return true;
                }
            }
        }
        return false;
    }

    private void unlinkNode(Node node) {
        if (node == first)
            removeFirst();
        else if (node == last)
            removeLast();
        else
            removeMiddle(node);
    }

    private void removeMiddle(Node node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;

        node.value = null;
        node.previous = null;
        node.next = null;
        size--;
    }

    @Override
    public Aircraft getFirst() {
        checkIfEmpty();
        return first.value;
    }

    @Override
    public Aircraft getLast() {
        checkIfEmpty();
        return last.value;
    }

    @Override
    public Aircraft get(int index) {
        return getNode(index).value;
    }

    @Override
    public Aircraft get(Aircraft element) {
        if (element == null) return null;

        for (Node node = first; node != null; node = node.next) {
            if (element.equals(node.value))
                return node.value;
        }
        return null;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + " out of range for size " + size);

        Node node = first;
        for (int i = 0; i < index; i++)
            node = node.next;
        return node;
    }

    public Aircraft set(int index, Aircraft element) {
        Node node = getNode(index);
        Aircraft oldVal = node.value;
        node.value = element;
        return oldVal;
    }

    @Override
    public int indexOf(Aircraft element) {
        int i = 0;
        if (element == null) {
            for (Node node = first; node != null; node = node.next) {
                if (node.value == null) return i;
                i++;
            }
        } else {
            for (Node node = first; node != null; node = node.next) {
                if (element.equals(node.value)) return i;
                i++;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Aircraft element) {
        return indexOf(element) != -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (Node node = first; node != null; ) {
            Node next = node.next;
            node.value = null;
            node.next = null;
            node.previous = null;
            node = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return first == null && size == 0;
    }

    private void checkIfEmpty() {
        if (isEmpty()) throw new IllegalStateException("List is empty");
    }

    @Override
    public Aircraft[] toArray() {
        Aircraft[] values = new Aircraft[size];

        int i = 0;
        for (var node = first; node != null; node = node.next)
            values[i++] = node.value;
        return values;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (var node = first; node != null; node = node.next) {
            builder.append("{").append(node.value).append("}");
            if (node != last)
                builder.append(",\n");
        }
        return builder.append("]").toString();
    }

    @Override
    public Iterator<Aircraft> iterator() {
        return new IteratorImpl();
    }

    private class IteratorImpl implements Iterator<Aircraft> {
        private Node current = first;
        private Node lastReturned;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Aircraft next() {
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = current;
            current = current.next;
            return lastReturned.value;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();

            unlinkNode(lastReturned);
            lastReturned = null;
        }
    }
}
