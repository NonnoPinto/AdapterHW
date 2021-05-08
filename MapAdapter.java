package myAdapter;

import java.util.*;

public class MapAdapter implements HMap {

    private Hashtable hash;

    // default contructor
    public MapAdapter() {
        hash = new Hashtable();
    }

    // copy construcor
    public MapAdapter(MapAdapter m) {
        hash = m.hash;
    }

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
        // keys
        HSet oldKeys = t.keySet();
        HIterator i = oldKeys.iterator();
        // values
        HCollection oldValues = t.values();
        HIterator v = oldValues.iterator();

        while (i.hasNext() && v.hasNext())
            this.put(i.next(), v.next());
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

    private class SetAdapter implements HSet {

        private Vector mySet;

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
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public HIterator iterator() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object[] toArray() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object[] toArray(Object[] a) throws ArrayStoreException, NullPointerException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean add(Object o) throws UnsupportedOperationException, ClassCastException, NullPointerException,
                IllegalArgumentException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean remove(Object o) throws ClassCastException, NullPointerException, UnsupportedOperationException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean containsAll(HCollection c) throws ClassCastException, NullPointerException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean addAll(HCollection c) throws UnsupportedOperationException, ClassCastException,
                NullPointerException, IllegalArgumentException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean retainAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean removeAll(HCollection c)
                throws UnsupportedOperationException, ClassCastException, NullPointerException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void clear() throws ClassCastException {
            // TODO Auto-generated method stub

        }

    }

    
    private class EntryAdapter implements Map.Entry{

        @Override
        public Object getKey() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object getValue() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object setValue(Object value) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean equals(Object o){
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int hashCode(){
            // TODO Auto-generated method stub
            return -1;
        }
    }
        

    private class Subber {
        
        Hashtable hash;
        MapAdapter original;

        Subber(Hashtable entry, MapAdapter o) {
            hash = new Hashtable();
            original = o;
        }
    }
}