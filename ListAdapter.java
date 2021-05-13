package myAdapter;

import java.util.*;

import javax.lang.model.element.Element;

//There arent any ClassCastExcpetion (and similar exception) cuz we r supposing everything is Object

public class ListAdapter implements HList {

    private Vector myVec; // member variable, vector
    // next two variables are just for sublist. If they r both null, its a normal
    // List object
    // clone != null AND sub == null --> original list the sub was made from
    // clone == null AND sub != null --> subList with a reference to the original
    // we cant have both != null cuz methods dont implement a sublist froma sublist
    private Subber sub; // helper class to save all sublist datas
    private ListAdapter clone; // reference to the sublist class: the "original" List tracks the new sublist
                               // (yes, its a stub)

    // With followings constructors, "myVec" will always be the more "usefull"
    // vector (the full one if theros no sub, the sub if theres one)
    // If a method make a non-structural change in a sublist (or in a list with a
    // sublist), its automatically made also on the other one
    // For structural methods (like add, remove etc...) we dont have any backing
    // implementation, for the documentation, saying "The semantics of the list
    // returned by this method become undefined if the backing list (i.e., this
    // list) is structurally modified in any way other than via the returned list"

    // default constructor
    public ListAdapter() {
        myVec = new Vector();
        sub = null;
        clone = null;
    }

    // copy constructor
    public ListAdapter(ListAdapter list) {
        myVec = list.myVec;
        sub = null;
        clone = null;
    }

    // subList helper constructor
    public ListAdapter(int from, int to, ListAdapter l, Vector a) {
        myVec = a;
        sub = new Subber(from, to, l);
        clone = null;
    }

    @Override
    public int size() {
        return this.myVec.size();
    }

    @Override
    public boolean isEmpty() {
        return this.myVec.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null)
            throw new NullPointerException();

        return this.myVec.contains(o);
    }

    @Override
    public HIterator iterator() {
        IteratorAdapter iter = new IteratorAdapter(this);
        return iter;
    }

    @Override
    public Object[] toArray() {
        Object[] myArray = new Object[this.myVec.size()];
        this.myVec.copyInto(myArray);
        return myArray;
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a == null)
            throw new NullPointerException();
        if (!(a instanceof Object[]))
            throw new ArrayStoreException();

        if (a.length < this.myVec.size())
            a = new Object[this.myVec.size()];

        this.myVec.copyInto(a);

        return a;
    }

    @Override
    public boolean add(Object o) {
        if (o == null)
            throw new NullPointerException();

        // check if this ListAdapater is a sublist
        // if true, also original list is modified
        if (this.sub != null) {
            this.sub.original.myVec.insertElementAt(o, this.sub.to);
            this.sub.to++; // and update indexTo
        }

        // add o to list
        this.myVec.addElement(o);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
            throw new NullPointerException();
        // check if this ListAdapater is a sublist
        if (this.myVec.contains(o) && this.sub != null) {
            this.sub.original.myVec.removeElementAt(this.myVec.indexOf(o) + this.sub.from);
            if (this.myVec.indexOf(o) == 0)
                this.sub.from++;
            else
                this.sub.to--;
        }

        // remove o from list
        return this.myVec.removeElement(o);

    }

    @Override
    public boolean containsAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (c.isEmpty())
            return false;

        HIterator tmp = c.iterator();

        while (tmp.hasNext())
            if (!(this.myVec.contains(tmp.next()))) // when a single element doesnt match, return false
                return false;

        return true;
    }

    @Override
    public boolean addAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        else if (c.isEmpty())
            return false;

        int oldSize;
        // check if this ListAdapater has a sublist
        // if true, also sublist is modified
        if (this.sub != null) {
            HIterator tmpSub = c.iterator();
            while (tmpSub.hasNext()) {
                this.sub.original.myVec.insertElementAt(tmpSub.next(), this.sub.to); // add o to sublist
                this.sub.to++; // and update indexTo
            }
        }

        // add to list
        oldSize = this.myVec.size(); // save old size
        HIterator tmp = c.iterator();
        while (tmp.hasNext())
            this.myVec.addElement(tmp.next());
        if (this.myVec.size() == oldSize) // if it has still the same size, nothing has been deleted
            return false;

        return true;
    }

    @Override
    public boolean addAll(int index, HCollection c) {
        if (c == null)
            throw new NullPointerException();
        else if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();
        else if (c.isEmpty())
            return false;

        int oldSize;
        if (this.sub != null) {
            HIterator tmpSub = c.iterator();
            int tmpIndex = index;
            while (tmpSub.hasNext()) {
                this.sub.original.myVec.insertElementAt(tmpSub.next(), tmpIndex + this.sub.from);
                tmpIndex++;
                this.sub.to++;
            }
        }

        oldSize = this.myVec.size(); // save old size
        HIterator tmp = c.iterator();
        while (tmp.hasNext()) {
            this.myVec.insertElementAt(tmp.next(), index);
            index++;
        }
        if (this.myVec.size() == oldSize) // if it has still the same size, nothing has been deleted
            return false;

        return true;
    }

    @Override
    public boolean removeAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (c.isEmpty())
            return false;

        int oldSize;
        // remove all from sublist
        if (this.clone != null) {
            oldSize = this.clone.myVec.size();
            this.clone.removeAll(c);
            for (int i = 0; i < (oldSize - this.clone.myVec.size()); i++)
                this.clone.sub.to--;
        }

        oldSize = this.myVec.size(); // save old size
        HIterator tmp = c.iterator();
        Object elem;
        while (tmp.hasNext()) { // ...removing...
            elem = tmp.next();
            while (this.myVec.contains(elem))
                this.myVec.removeElement(elem);
        }

        if (this.myVec.size() == oldSize) // if it has still the same size, nothing has been deleted
            return false;

        return true;
    }

    @Override
    public boolean retainAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (c.isEmpty())
            return false;

        int oldSize = this.myVec.size();

        HIterator tmp = this.iterator();
        while (tmp.hasNext()) {
            Object elem = tmp.next();
            if (this.sub != null) {
                if (!(c.contains(elem))) {
                    this.sub.original.myVec.removeElementAt(this.myVec.indexOf(elem) + this.sub.from);
                    this.sub.to--;
                }
            }
            if (!(c.contains(elem))) // if list doesnt have that element
                this.myVec.removeElement(elem); // then remove it from this list
        }

        if (this.myVec.size() == oldSize) // if it has still the same size, nothing has been deleted
            return false;

        return true;
    }

    @Override
    public void clear() {
        if (this.sub != null) {
            for (int i = this.sub.to-1; i >= this.sub.from; i--)
                this.sub.original.myVec.removeElementAt(i);
            this.sub.original.clone = null;
            this.sub = null;
        }

        this.myVec.removeAllElements();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ListAdapter) { // o is istnace of ListAdpter
            HIterator myTmp = this.iterator();
            HIterator tmp = ((ListAdapter) o).iterator();
            while (myTmp.hasNext() && tmp.hasNext()) // if a single element doesnt match, return false
                if (!(myTmp.next().equals(tmp.next())))
                    return false;

            if (myTmp.hasNext() || tmp.hasNext())
                return false;
        } else // o !(istance of) ListAdapter
            return false;

        return true; // o is istance of ListAdapter AND all elements match at the same position
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        HIterator i = this.iterator();
        while (i.hasNext()) {
            Object obj = i.next();
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        }
        return hashCode;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();

        return this.myVec.elementAt(index);
    }

    @Override
    public Object set(int index, Object element) {
        if (element == null)
            throw new NullPointerException();
        else if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();

        Object old;

        // check if this is the original list of a sub list
        if (this.clone != null && index > this.clone.sub.from && index < this.clone.sub.to) {
            old = this.clone.set(index - this.clone.sub.from, element); // stub on the ListAdapter object with both
                                                                        // sublist and
            // original list
            return old;
        }

        // check if the list has her own sublist
        if (this.sub != null)
            this.sub.original.myVec.setElementAt(element, index + this.sub.from); // setting element at [i + fromIndex]
                                                                                  // position on original list

        // here we know its a normal list without any sublist
        old = this.myVec.elementAt(index); // saving the old element
        this.myVec.setElementAt(element, index); // setting the new one

        return old;
    }

    @Override
    public void add(int index, Object element) {
        if (element == null)
            throw new NullPointerException();
        else if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();

        // sublist
        if (this.sub != null) {
            this.sub.original.myVec.insertElementAt(element, index + this.sub.from);
            this.sub.to++;
        }

        // list
        this.myVec.insertElementAt(element, index);
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();

        // sublist
        if (this.sub != null) {
            this.sub.original.myVec.removeElementAt(index + this.sub.from);
        }

        // list
        Object old = this.myVec.elementAt(index); // saving old element, since remove from J2SE 1.1 is void
        this.myVec.removeElementAt(index); // ...removing...

        return old;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null)
            throw new NullPointerException();

        int i = this.myVec.indexOf(o);

        if (!(sub == null))
            i = i + this.sub.from;

        return i;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null)
            throw new NullPointerException();

        int in = -1;
        for (int i = 0; i < this.myVec.size(); i++)
            if (o == this.myVec.elementAt(i))
                in = i;

        return in;
    }

    @Override
    public HListIterator listIterator() {
        ListIteratorAdapter iter = new ListIteratorAdapter(this);
        return iter;
    }

    @Override
    public HListIterator listIterator(int index) {
        if (index < 0 || index > this.myVec.size())
            throw new IndexOutOfBoundsException();

        ListIteratorAdapter iter = new ListIteratorAdapter(this);

        for (int i = 0; i < index; i++)
            iter.next();

        return null;
    }

    /*
     * Sublist method logic: makes the sublist in a new vector and fill it
     * inizitalize a new ListAdapter with - from and to index - old ListAdapater
     * object (i.e., this list) - sublist save new ListAdapter as "clone" in
     * original ListAdapter
     * 
     * Doing this, we can always know if any ListAdapter is a sublist or has one by
     * checking "sub" and "clone" and with them we can easly find sublist from
     * original and viceversa
     * 
     * One list does not support two different sublist
     */

    @Override
    public HList subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > this.myVec.size() || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();

        // check if its already a sublist
        if (this.clone != null)
            throw new UnsupportedOperationException("Cant make a sublist from a list who already has one");

        Vector subVec = new Vector(); // subvecotr

        for (int i = fromIndex; i < toIndex; i++)
            subVec.addElement(this.myVec.elementAt(i)); // filling the subvector

        ListAdapter sub = new ListAdapter(fromIndex, toIndex, this, subVec);

        this.clone = sub;

        return sub;
    }

    private class IteratorAdapter implements HIterator {
        private int index;
        private boolean next;
        ListAdapter list;

        IteratorAdapter(ListAdapter l) {
            list = new ListAdapter(l);
            index = -1;
            next = false;
        }

        public boolean hasNext() {
            return (index < list.myVec.size() - 1);
        }

        public Object next() {
            if (index == list.myVec.size() - 1)
                throw new NoSuchElementException();

            next = true;
            return list.myVec.elementAt(++index);
        }

        public void remove() {
            if (next == false || index < 0)
                throw new IllegalStateException();

            next = false;
            list.myVec.removeElementAt(index);
            index--;
        }
    }

    private class ListIteratorAdapter implements HListIterator {

        IteratorAdapter iter;
        private boolean check;

        ListIteratorAdapter(ListAdapter l) {
            iter = new IteratorAdapter(l);
            check = false;
        }

        public void add(Object o) {
            check = false;
            int i;
            if (this.iter.index == -1)
                i = 0;
            else
                i = this.iter.index;
            this.iter.list.myVec.insertElementAt(o, i);
        }

        public boolean hasNext() {
            return (this.iter.index < this.iter.list.myVec.size() - 1);
        }

        public boolean hasPrevious() {
            return (this.iter.index > 0);
        }

        public Object next() {
            check = true;
            return this.iter.next();
        }

        public int nextIndex() {
            int myIndex;
            if (this.iter.index == this.iter.list.myVec.size() - 1)
                myIndex = this.iter.index;
            else
                myIndex = this.iter.index + 1;

            return myIndex;
        }

        public Object previous() {
            if (this.iter.index <= 0)
                throw new NoSuchElementException();

            check = true;
            int i = this.iter.index--;
            return (this.iter.list.myVec.elementAt(i));
        }

        public int previousIndex() {
            int myIndex;
            if (this.iter.index == -1)
                myIndex = -1;
            else
                myIndex = this.iter.index - 1;

            return myIndex;
        }

        public void remove() {
            if (check == false || this.iter.index < 0)
                throw new IllegalStateException();

            check = false;

            this.iter.list.myVec.removeElementAt(this.iter.index);
        }

        public void set(Object o) {
            if (check == false || this.iter.index < 0)
                throw new IllegalStateException();

            check = false;

            this.iter.list.myVec.setElementAt(o, this.iter.index);
        }
    }

    private class Subber {
        protected int from;
        protected int to;
        ListAdapter original;

        Subber(int f, int t, ListAdapter o) {
            from = f;
            to = t;
            original = o;
        }
    }
}