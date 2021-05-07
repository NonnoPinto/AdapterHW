package myAdapter;

import java.util.*;

/**
 * An iterator over a collection. Iterator takes the place of Enumeration in the
 * Java collections framework. Iterators differ from enumerations in two ways:
 * Iterators allow the caller to remove elements from the underlying collection
 * during the iteration with well-defined semantics. Method names have been
 * improved.
 */

public interface HIterator {

    /**
     * Returns true if the iteration has more elements. (In other words, returns
     * true if next() would return an element rather than throwing an exception.)
     * 
     * @return true if the iteration has more elements
     */
    public boolean hasNext();

    /**
     * Returns the next element in the iteration.
     * 
     * @return the next element in the iteration.
     * @throws NoSuchElementException if the iteration has no more elements.
     */
    public Object next();

    /**
     * Removes from the underlying collection the last element returned by this
     * iterator (optional operation). This method can be called only once per call
     * to next(). The behavior of an iterator is unspecified if the underlying
     * collection is modified while the iteration is in progress in any way other
     * than by calling this method, unless an overriding class has specified a
     * concurrent modification policy
     * 
     * @throws IllegalStateException         if the next method has not yet been
     *                                       called, or the remove method has
     *                                       already been called after the last call
     *                                       to the next method.
     * @throws UnsupportedOperationException if the remove operation is not
     *                                       supported by this iterator.
     */
    public void remove();
}