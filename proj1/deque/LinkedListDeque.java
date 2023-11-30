package deque;
import java.util.*;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private class Node{

        private Node prev;
        private T item;
        private Node next;
        Node(){
            item = null;
            prev = next = null;
        }
        Node(Node prev, T item, Node next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }
    private Node sentinel;
    private int size;


    public LinkedListDeque(){
        size =0;
        sentinel = new Node();
        sentinel.prev = sentinel.next = sentinel;

    }
    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel){
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public boolean isEmpty() {
        return Deque.super.isEmpty();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        Node p = sentinel.next;
        while (p != sentinel && index > 0) {
            p = p.next;
            index--;
        }
        return (index == 0) ? (T) p.item : null;
    }

    @Override
    public void addFirst(T item) {
        Node tmp = new Node(sentinel,item,sentinel.next);
        /*
        tmp.next = sentinel.prev;
        sentinel.prev = tmp;
        is wrong, because sentinel should not server as Node which can be used
        we use sentinel.next to use
        and important,the best way is let tmp as the Node wait the Link to manipulate,we not access tmp.prev or tmp.next
        as well as,when we initiate the Node,we have let Node point to some pointer
         */
        sentinel.next.prev = tmp;
        sentinel.next = tmp;

        size +=1;

    }

    @Override
    public void addLast(T item) {
        Node tmp = new Node(sentinel.prev,item,sentinel);
        sentinel.prev.next = tmp;
        sentinel.prev = tmp;

        size += 1;

    }

    @Override
    public T removeFirst() {
        Node tmp = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size = (size == 0) ? size : size - 1;

        return(T) tmp.item;
    }

    @Override
    public T removeLast() {
        Node tmp = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;

        size = (size == 0) ? size : size - 1;
        return (T) tmp.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    private class LinkedListDequeIterator<T> implements Iterator<T>{
        private Node ptr;
        LinkedListDequeIterator(){
            ptr = sentinel.next;
        }
        @Override
        public boolean hasNext(){
            return ptr != sentinel;
        }
        @Override
        public T next(){
            T item = (T) ptr.item;
            ptr = ptr.next;
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

        Node p = sentinel.next;
        for (int i = 0; i < size; i++) {
            if (!p.item.equals(other.get(i))) {
                return false;
            }
            p = p.next;
        }
        return true;
    }

}
