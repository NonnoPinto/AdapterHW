package myAdapter;

import java.util.*;

public interface HIterator {

    /**
     * Returns true if the iteration has more elements. (In other words, returns true if next() would return an element rather than throwing an exception.)
     * @param key key whose presence in this map is to be tested. 
     * @return true if the iteration has more elements
     */
    public boolean hasNext();

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration.
     * @throws NoSuchElementException - if the iteration has no more elements.
     */
    public Object next() throws NoSuchElementException;


    /**
     * Removes from the underlying collection the last element returned by this iterator (optional operation). This method can be called only once per call to next().
     * The behavior of an iterator is unspecified if the underlying collection is modified while the iteration is in progress in any way other than by calling this method, unless an overriding class has specified a concurrent modification policy. 
     * @implSpec The default implementation throws an instance of UnsupportedOperationException and performs no other action.
     * @throws IllegalStateException - if the next method has not yet been called, or the remove method has already been called after the last call to the next method.
     * @throws UnsupportedOperationException - if the remove operation is not supported by this iterator.
     */
    public void remove() throws IllegalStateException, UnsupportedOperationException;    
}