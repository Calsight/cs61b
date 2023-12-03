package deque;
import java.util.*;
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    protected int size;
    protected T[] items;
    protected int nextFirst;
    protected int nextLast;

    public ArrayDeque(){
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }

    protected void checkMul() {
        if (size == items.length) {
            resize(size * 2);
        }
    }
    protected void checkDiv(){
        int len = items.length;
        if (len >= 16 && size < len/4){
            resize(len / 4);
        }
    }
    protected int addOne(int index) {
        return (index + 1) % items.length;
    }
    protected int minusOne(int index) {
        return (index + items.length - 1) % items.length;
    }

    protected void resize(int capacity) {
        T[] resized = (T[]) new Object[capacity];

        int index = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            resized[i] = items[index];
            index = addOne(index);
        }

        nextFirst = capacity - 1;
        nextLast = size;
        items = resized;
    }


    @Override
    public void addFirst(T item){
        checkMul();

        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;

    }

    @Override
    public void addLast(T item) {
        checkMul();

        items[nextLast] = item;
        nextLast = addOne(nextLast);
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        checkDiv();

        nextFirst = addOne(nextFirst);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        checkDiv();

        nextLast = minusOne(nextLast);
        T item = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return item;

    }

    @Override
    public T get(int index) {
        return items[(nextFirst + 1 + index) % items.length];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int index = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[index] + " ");
            index = addOne(index);
        }
        System.out.println();
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    protected class ArrayDequeIterator implements Iterator<T>{
        private int ptr;

        ArrayDequeIterator() {
            ptr = addOne(nextFirst);
        }
        public boolean hasNext() {
            return ptr != nextLast;
        }
        public T next() {
            T item =  items[ptr];
            ptr = addOne(ptr);
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque other = (Deque) o;
        if (size != other.size()) {
            return false;
        }

        int index = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            if (!(items[index].equals(other.get(i)))) {
                return false;
            }
            index = addOne(index);
        }
        return true;
    }
}
