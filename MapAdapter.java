package myAdapter;

import java.util.*;

//There arent any ClassCastExcpetion (and similar exception) cuz we r supposing everything is Object
//thorugh code, ive often used "this". I know it does not help to make code easy to read, but i choose this way to help understanding the code, since inve used very similar variable names

public class MapAdapter implements HMap {

    // member variable
    private Hashtable hash; // the hastable
    private SetAdapter keySet; // possible reference to a keySet for backing
    private SetAdapter entrySet; // possible reference to an entrySet for backing
    private SetAdapter valueCol; // possible reference to a value collection for backing

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
        keySet = m.keySet;
        entrySet = m.entrySet;
        valueCol = m.valueCol;
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
        // backing
        if (keySet != null && !(keySet.contains(key)))
            this.keySet.mySet.addElement(key);
        // backing
        if (valueCol != null) {
            Object tmp;
            if (this.hash.containsKey(key)) {
                tmp = this.hash.get(key);
                if (this.valueCol.contains(tmp))
                    this.valueCol.mySet.remove(tmp);
            }
            this.valueCol.mySet.addElement(value);
        }
        // backing
        if (entrySet != null) {
            EntryAdapter tmp = new EntryAdapter(key, value);
            Object tmpV = this.hash.get(key);
            if (tmpV != null) {
                EntryAdapter toRemove = new EntryAdapter(key, tmpV);
                this.entrySet.mySet.removeElement(toRemove);
            }
            this.entrySet.mySet.addElement(tmp);
        }

        return this.hash.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        if (key == null)
            throw new NullPointerException();
        // backing
        if (keySet != null)
            this.keySet.mySet.removeElement(key);
        // backing
        if (entrySet != null) {
            EntryAdapter tmp = new EntryAdapter(key, this.hash.get(key));
            this.entrySet.mySet.removeElement(tmp);
        }
        // backing
        if (valueCol != null)
            this.valueCol.mySet.removeElement(this.hash.get(key));

        return this.hash.remove(key);
    }

    @Override
    public void putAll(HMap t) {

        if (t == null)
            throw new NullPointerException();

        MapAdapter myT = (MapAdapter) t;
        // keys and values set
        HSet tmpKeySet;
        HCollection tmpValueSet;
        try {
            tmpKeySet = myT.keySet();
        } catch (UnsupportedOperationException nsee) {
            tmpKeySet = myT.keySet;
        }
        try {
            tmpValueSet = myT.values();
        } catch (UnsupportedOperationException nsee) {
            tmpValueSet = myT.valueCol;
        }
        // keys and values iterator
        HIterator iterK = tmpKeySet.iterator();
        HIterator iterV = tmpValueSet.iterator();

        // since iterator does not have "previous" or "get", i need to save keys and
        // values in order to use the multiple times
        Object myK;
        Object myV;

        // iterator along argoument map
        while (iterK.hasNext() && iterV.hasNext()) {
            myK = iterK.next();
            myV = iterV.next();

            this.put(myK, myV);
        }
    }

    @Override
    public void clear() {
        // backing
        if (this.keySet != null && !(this.keySet.isEmpty()))
            this.keySet.clear();

        if (this.entrySet != null && !(this.entrySet.isEmpty()))
            this.entrySet.clear();

        if (this.valueCol != null && !(this.valueCol.isEmpty()))
            this.valueCol.clear();
        // every set to null
        this.keySet = null;
        this.entrySet = null;
        this.valueCol = null;
        // clear map
        this.hash.clear();
    }

    @Override
    public HSet keySet() {
        // preventing from making multiple key set, since mapadapter contain only one
        // reference to a key est (and they would be always the same)
        if (this.keySet != null)
            throw new UnsupportedOperationException("You already made a KeySet from this Map!");

        SetAdapter mySet = new SetAdapter(this, this.hash.keys(), true);

        // keySet method saves the keySet also inside tha MapAdapter Object, for backing
        // the true parameter means thats a keySet and not a value collection
        this.keySet = mySet;

        return mySet;
    }

    @Override
    public HCollection values() {
        // preventing from making multiple value collection, since mapadapter contain
        // only one reference to a value collection (and they would be always the same)
        if (this.valueCol != null)
            throw new UnsupportedOperationException("You already made a value collection from this map!");

        SetAdapter tmpCol = new SetAdapter(this, this.hash.elements(), false);
        // values method saves the values collection also inside tha MapAdapter Object,
        // for backing
        // the false parameter means thats a value collection and not a keyset
        this.valueCol = tmpCol;

        return tmpCol;
    }

    @Override
    public HSet entrySet() {
        // preventing from making multiple entrySet, since mapadapter contain
        // only one reference to an entrySet (and they would be always the same)
        if (this.entrySet != null)
            throw new UnsupportedOperationException("You already made a Set from this Map!");

        HSet thisKey;
        boolean tmp = false;
        if (this.keySet == null) {
            thisKey = this.keySet();
            tmp = true;
        } else
            thisKey = this.keySet;

        Vector vec = new Vector();

        HIterator iter = thisKey.iterator();
        // filling the set with entries
        while (iter.hasNext()) {
            Object tmpKey = iter.next();
            vec.addElement(new EntryAdapter(tmpKey, this.hash.get(tmpKey)));
        }
        // entryset method saves the entryset also inside tha MapAdapter Object, for
        // backing
        this.entrySet = new SetAdapter(this, vec, true);

        if (tmp)
            this.keySet = null;

        return this.entrySet;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MapAdapter)) // o !istanceOf MapAdapter
            return false;
        // o istanceOF MapAdapter
        else {
            // my entries
            HIterator myIter;

            if (this.entrySet == null) {
                myIter = this.entrySet().iterator();
                this.entrySet = null;
            } else
                myIter = this.entrySet.iterator();

            // argoument entries
            HIterator oIter;

            if (this.entrySet == null) {
                oIter = ((MapAdapter) o).entrySet().iterator();
                ((MapAdapter) o).entrySet = null;
            } else
                oIter = ((MapAdapter) o).entrySet.iterator();

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

        HIterator myIter;
        if (this.entrySet == null) {
            myIter = this.entrySet().iterator();
            this.entrySet = null;
        } else
            myIter = this.entrySet.iterator();

        while (myIter.hasNext())
            myHashCode += myIter.next().hashCode();

        return myHashCode;
    }

    private class IteratorAdapter implements HIterator {
        private int index;
        public SetAdapter set; // reference to the set it was made from

        IteratorAdapter() {
            set = new SetAdapter();
            index = -1;
        }

        IteratorAdapter(SetAdapter s) {
            set = new SetAdapter();
            set.mySet = s.mySet;
            set.isKey = s.isKey;
            set.isEntry = s.isEntry;
            set.map = s.map;
            index = -1;
        }

        public boolean hasNext() {
            return (index < this.set.size() - 1);
        }

        public Object next() {
            if (index >= (this.set.mySet.size() - 1))
                throw new NoSuchElementException();

            index++;

            return this.set.mySet.elementAt(index);
        }

        public void remove() {
            if (index < 0)
                throw new IllegalStateException();

            // we can alway suppose if we have a set, it was made from a map
            // since set is not only a map class, to be sure its a "mapset", we always check
            // for it

            // backing
            // entry iterator
            if (this.set.map != null && this.set.isEntry) {// entrySet
                Object key = ((EntryAdapter) this.set.mySet.elementAt(index)).getKey();
                this.set.map.remove(key);
            }
            // backing
            // key iterator
            else if (this.set.map != null && this.set.map.keySet != null && this.set.isKey) // keySet
                this.set.map.remove(this.set.mySet.elementAt(index));
            // backing
            // value iterator
            else if (this.set.map != null && this.set.map.keySet != null && !(this.set.isKey)) { // value collection
                // still no entry set
                if (this.set.map.entrySet == null) {
                    // create entry set
                    HSet tmpEntrySet = this.set.map.entrySet();
                    // delete the entrySet
                    this.set.map.entrySet = null;
                    // iterator on it
                    HIterator tmpEntryIter = tmpEntrySet.iterator();
                    // searching for index
                    Object myIndex = null;

                    while (tmpEntryIter.hasNext() || myIndex == null) {
                        EntryAdapter tmpEntry = (EntryAdapter) tmpEntryIter.next();
                        Object tmpKey = tmpEntry.getKey();
                        Object tmpValue = tmpEntry.getValue();
                        // if matching, save the index
                        if (tmpValue.equals(this.set.mySet.elementAt(index)))
                            myIndex = tmpKey;
                    }

                    this.set.map.remove(myIndex);
                }
                // already has an entry set
                else {
                    // iterator on it
                    HIterator tmpEntryIter = this.set.map.entrySet.iterator();
                    // searching for index
                    Object myIndex = null;
                    // search for the first matching value on this collection
                    while (tmpEntryIter.hasNext() || myIndex == null) {
                        // i need the entry, cuz map only works with keys
                        EntryAdapter tmpEntry = (EntryAdapter) tmpEntryIter.next();
                        Object tmpKey = tmpEntry.getKey();
                        Object tmpValue = tmpEntry.getValue();
                        // if matching, save the index
                        if (tmpValue.equals(this.set.mySet.elementAt(index)))
                            myIndex = tmpKey;
                    }

                    this.set.map.remove(myIndex);
                }
            }

            // this.set.mySet.removeElementAt(index);
            index--;
        }
    }

    private class SetAdapter implements HSet {

        public Vector mySet;
        public MapAdapter map;
        // isKey == true if its a KeySet, false if its a ValueSet
        boolean isKey;
        boolean isEntry;

        public SetAdapter() {
            mySet = new Vector();
            map = null;
        }

        public SetAdapter(MapAdapter m, Enumeration e, boolean b) {
            mySet = new Vector();
            // fill vector with enumearion
            while (e.hasMoreElements())
                mySet.addElement(e.nextElement());
            map = m;
            isKey = b;
            isEntry = false;
        }

        public SetAdapter(MapAdapter m, Vector v, boolean b) {
            mySet = v;
            map = m;
            isKey = false;
            isEntry = b;
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

            boolean ret = this.mySet.contains(o);

            if (this.map != null && o instanceof EntryAdapter) {
                Object key = ((EntryAdapter) o).getKey();
                this.map.remove(key);
            } else if (this.map != null && this.isKey) {
                this.map.remove(o);
            } else if (this.map != null && !(this.isKey)) {
                // still no entry set
                if (this.map.entrySet == null) {
                    // create entry set
                    HSet tmpEntrySet = this.map.entrySet();
                    // delete the entrySet
                    this.map.entrySet = null;
                    // iterator on it
                    HIterator tmpEntryIter = tmpEntrySet.iterator();
                    // searching for index
                    Object myIndex = null;

                    while (tmpEntryIter.hasNext() || myIndex == null) {
                        EntryAdapter tmpEntry = (EntryAdapter) tmpEntryIter.next();
                        Object tmpKey = tmpEntry.getKey();
                        Object tmpValue = tmpEntry.getValue();
                        // if matching, save the index
                        if (tmpValue.equals(o))
                            myIndex = tmpKey;
                    }

                    this.map.remove(myIndex);
                }
                // already has an entry set
                else {
                    // iterator on it
                    HIterator tmpEntryIter = this.map.entrySet.iterator();
                    // searching for index
                    Object myIndex = null;
                    // search for the first matching value on this collection
                    while (tmpEntryIter.hasNext() || myIndex == null) {
                        // i need the entry, cuz map only works with keys
                        EntryAdapter tmpEntry = (EntryAdapter) tmpEntryIter.next();
                        Object tmpKey = tmpEntry.getKey();
                        Object tmpValue = tmpEntry.getValue();
                        // if matching, save the index
                        if (tmpValue.equals(o))
                            myIndex = tmpKey;
                    }

                    this.map.remove(myIndex);
                }
            }

            return ret;
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
            if (this.map != null)
                throw new UnsupportedOperationException();

            if (c.isEmpty() || c.equals(null))
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
            if (c.isEmpty() || c.equals(null))
                return false;

            boolean hasMap = false;
            if (this.map != null)
                hasMap = true;

            int oldSize = this.mySet.size();

            HIterator iter;

            if (hasMap) {
                if (isKey) {
                    if (this.map.keySet == null) {
                        iter = this.map.keySet.iterator();
                        this.map.keySet = null;
                    } else
                        iter = this.map.keySet.iterator();
                } else if (isEntry) {
                    if (this.map.entrySet == null) {
                        iter = this.map.entrySet.iterator();
                        this.map.entrySet = null;
                    } else
                        iter = this.map.entrySet.iterator();
                } else {
                    if (this.map.valueCol == null) {
                        iter = this.map.valueCol.iterator();
                        this.map.valueCol = null;
                    } else
                        iter = this.map.valueCol.iterator();
                }
                while (iter.hasNext())
                    if (!(c.contains(iter.next())))
                        iter.remove();
            }

            if (this.mySet.size() == oldSize) // if it has still the same size, nothing has been deleted
                return false;

            return true;
        }

        @Override
        public boolean removeAll(HCollection c) {
            if (c.equals(null) || c.isEmpty())
                return false;

            boolean hasMap = false;
            if (this.map != null)
                hasMap = true;

            int oldSize = this.mySet.size();

            HIterator iter;
            HIterator argIter = c.iterator();

            if (hasMap) {
                if (isKey) {
                    if (this.map.keySet == null) {
                        iter = this.map.keySet.iterator();
                        this.map.keySet = null;
                    } else
                        iter = this.map.keySet.iterator();
                } else if (isEntry) {
                    if (this.map.entrySet == null) {
                        iter = this.map.entrySet.iterator();
                        this.map.entrySet = null;
                    } else
                        iter = this.map.entrySet.iterator();
                } else {
                    if (this.map.valueCol == null) {
                        iter = this.map.valueCol.iterator();
                        this.map.valueCol = null;
                    } else
                        iter = this.map.valueCol.iterator();
                }
                while (argIter.hasNext()){
                    Object tmp = argIter.next();
                    if (this.contains(tmp))
                        this.remove(tmp);
                }
            }

            if (this.mySet.size() == oldSize) // if it has still the same size, nothing has been deleted
                return false;

            return true;
        }

        @Override
        public void clear() {
            this.mySet.removeAllElements();
            this.map.clear();
        }

    }

    private class EntryAdapter implements Map.Entry {
        private Object myKey;
        private Object myValue;

        // default constructor
        public EntryAdapter() {
            myKey = null;
            myValue = null;
        }

        // constructor
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

            return (myO.getKey().equals(this.myKey) && myO.getValue().equals(this.myValue));
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