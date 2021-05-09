package myAdapter;

import java.util.*;

public class MapAdapter implements HMap {

    private Hashtable hash;
    private MapAdapter clone;

    // default contructor
    public MapAdapter() {
        hash = new Hashtable();
        clone = null;
    }

    // copy construcor
    public MapAdapter(MapAdapter m) {
        hash = m.hash;
        clone = null;
    }

    // helerp construcotr for backing
    /*
     * public MapAdapter(MapAdapter original, Hashtable entrySet){ hash = entrySet;
     * clone = null; *************C'E' QUALCOSA CHE MI SFUGGE**************** }
     */

    @Override
    public int size() {
        return this.hash.size();
    }

    @Override
    public boolean isEmpty() {
        return this.hash.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null)
            throw new NullPointerException();

        return this.hash.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null)
            throw new NullPointerException();

        return this.hash.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        if (key == null)
            throw new NullPointerException();

        return this.hash.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        if (key == null || value == null)
            throw new NullPointerException();

        return this.hash.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        if (key == null)
            throw new NullPointerException();

        return this.hash.remove(key);
    }

    @Override
    public void putAll(HMap t) {
        // TODO Auto-generated method stub
    }

    @Override
    public void clear() {
        this.hash.clear();
    }

    @Override
    public HSet keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HCollection values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HSet entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MapAdapter)) // o !istanceOf MapAdapter
            return false;
        else { // o istanceOF MapAdapter
               // my Map
            HIterator myIter = (this.entrySet()).iterator();
            // argoument map
            HIterator oIter = (((MapAdapter) o).entrySet()).iterator();

            // comparing Map.Entry
            while (myIter.hasNext() && oIter.hasNext())
                if (!(myIter.next().equals(oIter.next())))
                    return false;

            // both ended?
            if (myIter.hasNext() || oIter.hasNext())
                return false;
        }
        return true;
    }

    public int hashCode() {
        int myHashCode = 0;
        HIterator myIter = (this.entrySet()).iterator();

        while (myIter.hasNext())
            myHashCode += myIter.next().hashCode();

        return myHashCode;
    }

    private class IteratorAdapter implements HIterator {
        private int index;
        private boolean next;
        SetAdapter set;

        IteratorAdapter() {
            set = new SetAdapter();
        }

        IteratorAdapter(SetAdapter s) {
            set = new SetAdapter(s);
            index = -1;
        }

        public boolean hasNext() {
            return (index < this.set.size());
        }

        public Object next() {
            if (index == this.set.size())
                throw new NoSuchElementException();

            return this.set.mySet.elementAt(index++);
        }

        public void remove() {
            if (index < 0)
                throw new IllegalStateException();

            this.set.mySet.removeElementAt(index);
            index--;
        }
    }

    private class SetAdapter implements HSet {

        public Vector mySet;

        public SetAdapter() {
            mySet = null;
        }

        public SetAdapter(SetAdapter s) {
            mySet = s.mySet;
        }

        @Override
        public int size() {
            return mySet.size();
        }

        @Override
        public boolean isEmpty() {
            return mySet.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            if (o == null)
                throw new NullPointerException();

            return this.mySet.contains(o);
        }

        @Override
        public HIterator iterator() {
            IteratorAdapter iter = new IteratorAdapter(this);
            return iter;
        }

        @Override
        public Object[] toArray() {
            Object[] tmp = new Object[this.mySet.size()];
            this.mySet.copyInto(tmp);

            return tmp;
        }

        @Override
        public Object[] toArray(Object[] a) {
            if (a == null)
                throw new NullPointerException();
            if (!(a instanceof SetAdapter[]))
                throw new ArrayStoreException();

            if (a.length < this.mySet.size())
                a = new Object[this.mySet.size()];

            this.mySet.copyInto(a);

            return a;
        }

        @Override
        public boolean add(Object o) {
            if (o == null)
                throw new NullPointerException();

            if (this.mySet.contains(o))
                return false;

            this.mySet.addElement(o);

            return false;
        }

        @Override
        public boolean remove(Object o) {
            if (o == null)
                throw new NullPointerException();

            return this.mySet.removeElement(o);
        }

        @Override
        public boolean containsAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();

            HIterator iter = c.iterator();

            while (iter.hasNext())
                if (!(this.mySet.contains(iter.next())))
                    return false;

            return true;
        }

        @Override
        public boolean addAll(HCollection c) {
            if (c.contains(null))
                throw new NullPointerException();
            if (c == null)
                return false;

            int oldSize = this.mySet.size();

            HIterator iter = c.iterator();

            while (iter.hasNext())
                this.add(iter.next());

            if (this.mySet.size() == oldSize) // if it has still the same size, nothing has been deleted
                return false;

            return true;
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c.contains(null))
                throw new NullPointerException();
            if (c == null)
                return false;

            int oldSize = this.mySet.size();
            
            HIterator iter = c.iterator();
            Object tmp;
            while (iter.hasNext()){
                tmp = iter.next();
                if(!(this.mySet.contains(tmp)))
                    this.mySet.removeElement(tmp);
            }

            if (this.mySet.size() == oldSize) // if it has still the same size, nothing has been deleted
                return false;

            return true;
        }

        @Override
        public boolean removeAll(HCollection c) {
            if (c.contains(null))
                throw new NullPointerException();
            if (c == null)
                return false;

            int oldSize = this.mySet.size();

            HIterator iter = c.iterator();

            while (iter.hasNext())
                this.remove(iter.next());

            if (this.mySet.size() == oldSize) // if it has still the same size, nothing has been deleted
                return false;

            return false;
        }

        @Override
        public void clear() {
            this.mySet.clear();
        }

    }

    private class EntryAdapter implements Map.Entry {
        private Object myKey;
        private Object myValue;

        @Override
        public Object getKey() {
            return myKey;
        }

        @Override
        public Object getValue() {
            return myValue;
        }

        @Override
        public Object setValue(Object value) {
            if (value == null)
                throw new NullPointerException();

            Object oldValue = this.myValue;

            this.myValue = value;

            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof EntryAdapter))
                return false;

            EntryAdapter myO = (EntryAdapter) o;

            return (myO.getKey() == this.myKey && myO.getValue() == this.myValue);
        }

        @Override
        public int hashCode() {
            int keyCode;
            int valueCode;

            if (this.myKey == null)
                keyCode = 0;
            else
                keyCode = this.myKey.hashCode();

            if (this.myValue == null)
                valueCode = 0;
            else
                valueCode = this.myValue.hashCode();

            int code = keyCode | valueCode;
            return code;
        }
    }
}