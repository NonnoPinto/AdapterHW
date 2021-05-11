package myAdapter;

import java.util.*;

//There arent any ClassCastExcpetion (and similar exception) cuz we r supposing everything is Object
//thorugh code, ive often used "this". I know it does not help to make code easy to read, but i choose this way to help understanding the code, since inve used very similar variable names

public class MapAdapter implements HMap {

    // member variable
    private Hashtable hash; // the hastable
    private HSet keySet; // possible reference to a keySet for backing
    private HSet entrySet; // possible reference to an entrySet for backing
    private HCollection valueCol; // possible reference to a value collection for backing

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
        // backing
        if (keySet != null)
            this.keySet.add(key);
        // backing
        if (valueCol != null)
            this.valueCol.add(value);
        // backing
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
        // backing
        if (keySet != null)
            this.keySet.remove(key);
        // backing
        if (entrySet != null) {
            EntryAdapter tmp = new EntryAdapter(key, this.hash.get(key));
            this.entrySet.remove(tmp);
        }
        // backing
        if (valueCol != null)
            this.valueCol.remove(this.hash.get(key));

        return this.hash.remove(key);
    }

    @Override
    public void putAll(HMap t) {
        if (t == null)
            throw new NullPointerException();
        // boolean variables for backing
        // they were not necessary, but they make code lighter, for reading and
        // compiling
        boolean hasKeySet = this.keySet != null ? true : false;
        boolean hasValueCol = this.entrySet != null ? true : false;
        boolean hasEntrySet = this.valueCol != null ? true : false;

        // keys and values set
        HSet tmpKeySet = t.keySet();
        HCollection tmpentrySet = t.values();
        // keys and values iterator
        HIterator iterK = tmpKeySet.iterator();
        HIterator iterV = tmpentrySet.iterator();
        // since iterator does not have "previous" or "get", i need to save keys and
        // values in order to use the multiple times
        Object myK;
        Object myV;

        while (iterK.hasNext() && iterV.hasNext()) {
            myK = iterK.next();
            myV = iterV.next();
            // backing
            if (hasKeySet)
                this.keySet.add(myK);
            // backing
            if (hasEntrySet)
                this.entrySet.add(new EntryAdapter(myK, myV));
            // backing
            if (hasValueCol)
                this.valueCol.add(myV);

            this.put(myK, myV);
        }
    }

    @Override
    public void clear() {
        // backing
        if (this.keySet != null)
            this.keySet.clear();
        // backing
        if (this.entrySet != null)
            this.entrySet.clear();
        // backing
        if (this.valueCol != null)
            this.valueCol.clear();

        this.hash.clear();
    }

    @Override
    public HSet keySet() {
        // preventing from making multiple key set, since mapadapter contain only one
        // reference to a key est (and they would be always the same)
        if (this.keySet != null)
            throw new UnsupportedOperationException("You already made a KeySet from this Map!");

        HSet mySet = new SetAdapter(this, this.hash.keys(), true);

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

        HSet tmpCol = new SetAdapter(this, this.hash.elements(), false);
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

        HSet thisSet = new SetAdapter();

        HSet thisKey = this.keySet();
        HIterator iter = thisKey.iterator();
        // filling the set with entries
        while (iter.hasNext()) {
            Object tmpKey = iter.next();
            thisSet.add(new EntryAdapter(tmpKey, this.get(tmpKey)));
        }
        // entryset method saves the entryset also inside tha MapAdapter Object, for
        // backing
        this.entrySet = thisSet;

        return thisSet;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MapAdapter)) // o !istanceOf MapAdapter
            return false;
        // o istanceOF MapAdapter
        else {
            // my entries
            HIterator myIter = this.entrySet().iterator();

            // argoument entries
            HIterator oIter = ((MapAdapter) o).entrySet().iterator();

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

        HIterator myIter = this.entrySet().iterator();

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

            // we can alway suppose if we have a set, it was made from a map
            // since set is not only a map class, to be sure its a "mapset", we always check
            // for it

            // backing
            // entry iterator
            if (this.set.map != null && this.set.mySet.elementAt(index) instanceof EntryAdapter) {
                Object key = ((EntryAdapter) this.set.mySet.elementAt(index)).getKey();
                this.set.map.hash.remove(key);
            }
            // backing
            // key iterator
            else if (this.set.map != null && this.set.map.keySet != null && this.set.isKey)
                this.set.map.hash.remove(this.set.mySet.elementAt(index));
            // backing
            // value iterator
            else if (this.set.map != null && this.set.map.keySet != null && !(this.set.isKey)) {
                // still no entry set
                if (this.set.map.entrySet == null) {
                    // create entry set
                    HSet tmpEntrySet = this.set.map.entrySet();
                    // iterator on it
                    HIterator tmpEntryIter = tmpEntrySet.iterator();
                    // searching for index
                    Object myIndex = null;

                    while (tmpEntryIter.hasNext() || myIndex == null) {
                        EntryAdapter tmpEntry = (EntryAdapter) tmpEntryIter.next();
                        Object tmpKey = tmpEntry.getKey();
                        Object tmpValue = tmpEntry.getValue();
                        // if matching, save the index
                        if (tmpValue == this.set.mySet.elementAt(index))
                            myIndex = tmpKey;
                    }

                    this.set.map.hash.remove(myIndex);

                    // delete the entrySet
                    this.set.map.entrySet = null;
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
                        if (tmpValue == this.set.mySet.elementAt(index))
                            myIndex = tmpKey;
                    }

                    this.set.map.hash.remove(myIndex);
                }
            }

            this.set.mySet.removeElementAt(index);
            index--;
        }
    }

    private class SetAdapter implements HSet {

        public Vector mySet;
        public MapAdapter map;
        // isKey == true if its a KeySet, false if its a ValueSet
        boolean isKey;

        public SetAdapter() {
            mySet = null;
            map = null;
        }

        public SetAdapter(MapAdapter m, Enumeration e, boolean b) {
            // fill vector with enumearion
            while (e.hasMoreElements())
                mySet.addElement(e.nextElement());
            map = m;
            isKey = b;
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
                this.map.hash.remove(key);
            } else if (this.map != null && this.isKey) {
                this.map.hash.remove(o);
            } else if (this.map != null && !(this.isKey)) {
                // still no entry set
                if (this.map.entrySet == null) {
                    // create entry set
                    HSet tmpEntrySet = this.map.entrySet();
                    // iterator on it
                    HIterator tmpEntryIter = tmpEntrySet.iterator();
                    // searching for index
                    Object myIndex = null;

                    while (tmpEntryIter.hasNext() || myIndex == null) {
                        EntryAdapter tmpEntry = (EntryAdapter) tmpEntryIter.next();
                        Object tmpKey = tmpEntry.getKey();
                        Object tmpValue = tmpEntry.getValue();
                        // if matching, save the index
                        if (tmpValue == o)
                            myIndex = tmpKey;
                    }

                    this.map.hash.remove(myIndex);

                    // delete the entrySet
                    this.map.entrySet = null;
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
                        if (tmpValue == o)
                            myIndex = tmpKey;
                    }

                    this.map.hash.remove(myIndex);
                }
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
            if (this.map != null)
                throw new UnsupportedOperationException();

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

            boolean hasMap = false;
            if (this.map != null)
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
                            this.map.hash.remove(tmpKey);
                        }
                        else if (this.isKey)
                            this.map.hash.remove(tmp);
                        else{
                            if (this.map.entrySet == null){
                                Object my_key = null;
                                //cerating an entryset
                                HSet tmpEntrySet = this.map.entrySet();
                                HIterator tmpEntryIter = tmpEntrySet.iterator();
                                while (tmpEntryIter.hasNext() || my_key == null)
                                    if (tmp == ((EntryAdapter)tmpEntryIter.next()).getValue())
                                        my_key = tmp;
                                this.map.remove(my_key);
                                //removing entryset
                                this.map.entrySet = null;
                            }
                            else{
                                Object my_key = null;
                                HIterator tmpEntryIter = this.map.entrySet.iterator();
                                while (tmpEntryIter.hasNext() || my_key == null)
                                    if (tmp == ((EntryAdapter)tmpEntryIter.next()).getValue())
                                        my_key = tmp;
                                this.map.remove(my_key);
                            }
                        }
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
                        //using the setadapter.remove
                        this.remove(tmp);
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

        //default constructor
        public EntryAdapter() {
            myKey = null;
            myValue = null;
        }

        //constructor
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