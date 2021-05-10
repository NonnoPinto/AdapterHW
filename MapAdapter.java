package myAdapter;

import java.util.*;

public class MapAdapter implements HMap {

    private Hashtable hash;
    private HSet keySet;
    private HSet entrySet;
    private HCollection valueCol;

    // default contructor
    public MapAdapter() {
        hash = new Hashtable();
        keySet = null;
        entrySet = null;
        valueCol = null;
    }

    // copy construcor
    public MapAdapter(MapAdapter m) {
        hash = m.hash;
        keySet = null;
        entrySet = null;
        valueCol = null;
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

        return this.hash.contains(value);
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

        if (keySet != null)
            this.keySet.add(key);

        if (entrySet != null) {
            EntryAdapter tmp = new EntryAdapter(key, value);
            this.entrySet.add(tmp);
        }

        return this.hash.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        if (key == null)
            throw new NullPointerException();

        if (keySet != null)
            this.keySet.remove(key);

        if (entrySet != null) {
            EntryAdapter tmp = new EntryAdapter(key, this.hash.get(key));
            this.entrySet.remove(tmp);
        }

        if (valueCol != null)
            this.valueCol.remove(this.hash.get(key));

        return this.hash.remove(key);
    }

    @Override
    public void putAll(HMap t) {
        if (t == null)
            throw new NullPointerException();

        boolean hasKeySet = false;
        if (this.keySet != null)
            hasKeySet = true;
        boolean hasEntrySet = false;
        if (this.entrySet != null)
            hasEntrySet = true;
        boolean hasValueCol = false;
        if (this.valueCol != null)
            hasValueCol = true;

        // keys and values iterator
        HSet tmpKeySet = t.keySet();
        HCollection tmpentrySet = t.values();

        HIterator iterK = tmpKeySet.iterator();
        HIterator iterV = tmpentrySet.iterator();

        Object myK;
        Object myV;

        while (iterK.hasNext() && iterV.hasNext()) {
            myK = iterK.next();
            myV = iterV.next();

            if (hasKeySet)
                this.keySet.add(myK);
            
            if (hasEntrySet) {
                EntryAdapter tmp = new EntryAdapter(myK, myV);
                this.entrySet.add(tmp);
            }

            if (hasValueCol)
                this.valueCol.add(myV);

            this.put(myK, myV);
        }
    }

    @Override
    public void clear() {
        if (this.keySet != null)
            this.keySet.clear();

        if (this.entrySet != null)
            this.entrySet.clear();
        
        if (this.valueCol != null)
            this.valueCol.clear();

        this.hash.clear();
    }

    @Override
    public HSet keySet() {
        if (this.keySet != null)
            throw new UnsupportedOperationException("You already made a KeySet from this Map!");

        if (this.entrySet != null)
            throw new UnsupportedOperationException("You already made a entrySet from this Map!");

        HSet mySet = new SetAdapter(this, this.hash.keys());
        this.keySet = mySet;

        return mySet;
    }

    /*
     * The collection supports element removal, which removes the corresponding
     * mapping from the map, via the Iterator.remove, Collection.remove
     * It does not support the add or addAll
     * operations.
     * DEVO CAPIRE COME DISTINGUERE CHIAVI E VALORI NELLE VARIE OPERAZIONI!!
     */
    @Override
    public HCollection values() {
        if (this.valueCol != null)
            throw new UnsupportedOperationException("You already made a value collection from this map!");
        
        HCollection tmpCol = (HCollection) this.hash.elements();
        
        this.valueCol = tmpCol;

        return tmpCol;
    }

    @Override
    public HSet entrySet() {
        if (this.entrySet != null)
            throw new UnsupportedOperationException("You already made a Set from this Map!");

        HSet thisSet = new SetAdapter();

        HSet thisKey = this.keySet();
        HIterator iter = thisKey.iterator();

        while (iter.hasNext()) {
            Object tmpKey = iter.next();
            Map.Entry tmpEntry = new EntryAdapter(tmpKey, this.get(tmpKey));
            thisSet.add(tmpEntry);
        }

        this.entrySet = thisSet;

        return thisSet;
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
        public SetAdapter set;

        IteratorAdapter() {
            set = new SetAdapter();
            index = -1;
        }

        IteratorAdapter(SetAdapter s) {
            set = s;
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

            if (this.set.map != null && this.set.mySet.elementAt(index) instanceof EntryAdapter) {
                Object key = ((EntryAdapter) this.set.mySet.elementAt(index)).getKey();
            } else if (this.set.map != null) {
                this.set.map.remove(this.set.mySet.elementAt(index));
            }

            this.set.mySet.removeElementAt(index);
            index--;
        }
    }

    private class SetAdapter implements HSet {

        public Vector mySet;
        public MapAdapter map;

        public SetAdapter() {
            mySet = null;
            map = null;
        }

        public SetAdapter(MapAdapter m, Enumeration e) {
            while (e.hasMoreElements())
                mySet.addElement(e.nextElement());
            map = m;
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

            if (this.map != null)
                throw new UnsupportedOperationException();

            if (this.mySet.contains(o))
                return false;

            this.mySet.addElement(o);

            return true;
        }

        @Override
        public boolean remove(Object o) {
            if (o == null)
                throw new NullPointerException();

            if (this.map != null && o instanceof EntryAdapter) {
                Object key = ((EntryAdapter) o).getKey();
            } else if (this.map != null) {
                this.map.remove(o);
            }

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

            if (this.map != null)
                throw new UnsupportedOperationException();

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

            boolean hasMap = false;
            if (this.map != map)
                hasMap = true;

            int oldSize = this.mySet.size();

            HIterator iter = c.iterator();
            Object tmp;

            while (iter.hasNext()) {
                tmp = iter.next();

                if (!(this.mySet.contains(tmp))) {
                    if (hasMap) {
                        if (tmp instanceof EntryAdapter) {
                            Object tmpKey = ((EntryAdapter) tmp).getKey();
                            this.map.remove(tmpKey);
                        } else
                            this.map.remove(tmp);
                    }

                    this.mySet.removeElement(tmp);
                }
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

            boolean hasMap = false;
            if (this.map != null)
                hasMap = true;

            int oldSize = this.mySet.size();

            HIterator iter = c.iterator();

            Object tmp;

            while (iter.hasNext()) {
                tmp = iter.next();

                if (this.mySet.contains(tmp)) {
                    if (hasMap) {
                        if (tmp instanceof EntryAdapter) {
                            Object tmpKey = ((EntryAdapter) tmp).getKey();
                            this.map.remove(tmpKey);
                        } else
                            this.map.remove(tmp);
                    }

                    this.mySet.removeElement(tmp);
                }
            }

            if (this.mySet.size() == oldSize) // if it has still the same size, nothing has been deleted
                return false;

            return false;
        }

        @Override
        public void clear() {
            this.mySet.clear();
            this.map.clear();
        }

    }

    private class EntryAdapter implements Map.Entry {
        private Object myKey;
        private Object myValue;

        public EntryAdapter(Object key, Object value) {
            myKey = key;
            myValue = value;
        }

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