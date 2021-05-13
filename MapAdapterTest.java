package myTest;

import myAdapter.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class MapAdapterTest {

    MapAdapter mapTest;
    // Set
    HSet entrySet;
    HSet keySet;
    HCollection valueSet;
    // Iterators
    HIterator entryIter;
    HIterator valueIter;
    HIterator keyIter;

    @Before
    public void init() {
        mapTest = new MapAdapter();
    }

    public void fill() {
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            mapTest.put(i, tmp);
        }
    }

    // MapAdapter Test
    @Test
    public void sizeTest() {
        assertEquals(mapTest.size(), 0);
        fill();
        assertEquals(mapTest.size(), 20);
    }

    @Test
    public void emptyTest() {
        assertTrue(mapTest.isEmpty());
        fill();
        assertFalse(mapTest.isEmpty());
    }

    @Test
    public void containsKeyTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.containsKey(null);
        });
        mapTest.put("chiave", "valore");
        assertTrue(mapTest.containsKey("chiave"));
    }

    @Test
    public void containsValueTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.containsValue(null);
        });
        mapTest.put("chiave", "valore");
        assertTrue(mapTest.containsValue("valore"));
    }

    @Test
    public void getTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.get(null);
        });
        mapTest.put("chiave", "valore");
        assertEquals("valore", mapTest.get("chiave"));
    }

    @Test
    public void putTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.put(null, "valore");
        });
        assertThrows(NullPointerException.class, () -> {
            mapTest.put("chiave", null);
        });
        assertEquals(null, mapTest.put("chiave", "valore"));
        assertEquals("valore", mapTest.put("chiave", "secondo valore"));
        assertEquals("secondo valore", mapTest.get("chiave"));

        init();
        fill();
        createIterator();
        Object obj = entryIter.next();
        // prebacking
        assertFalse(valueSet.contains("valore diverso"));
        assertFalse(keySet.contains(20));
        // entry backing
        assertTrue(entrySet.contains(obj));
        // put()
        assertEquals("valore n. 2", mapTest.put(2, "valore diverso"));
        assertEquals(null, mapTest.put(20, "fuori dal fill()"));
        assertTrue(mapTest.containsValue("valore diverso"));
        // backing
        assertTrue(valueSet.contains("valore diverso"));
        assertTrue(keySet.contains(20));

    }

    @Test
    public void removeTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.remove(null);
        });
        assertEquals(null, mapTest.remove("chiave"));
        mapTest.put("chiave", "valore");
        assertEquals("valore", mapTest.remove("chiave"));

        init();
        fill();
        createIterator();
        // prebacking
        assertEquals(null, mapTest.remove(20));
        assertTrue(keySet.contains(19));
        assertTrue(valueSet.contains("valore n. 19"));
        Object obj = entryIter.next();
        assertTrue(entrySet.contains(obj));
        // remove
        assertEquals("valore n. 19", mapTest.remove(19));
        // backing
        assertFalse(keySet.contains(19));
        assertFalse(valueSet.contains("valore n. 19"));
        assertFalse(entrySet.contains(obj));
    }

    @Test
    public void putAllTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.putAll(null);
        });

        fill();

        MapAdapter myMap = new MapAdapter();
        
        createIterator();

        myMap.putAll(mapTest);
        
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            assertEquals(tmp, myMap.get(i));
        }

        // backing check
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            assertTrue(valueSet.contains(tmp));
            assertTrue(keySet.contains(i));
        }
    }

    @Test
    public void clearTest() {
        fill();

        createIterator();

        assertFalse(mapTest.isEmpty());
        // backing
        assertFalse(keySet.isEmpty());
        assertFalse(valueSet.isEmpty());
        assertFalse(entrySet.isEmpty());

        mapTest.clear();

        assertTrue(mapTest.isEmpty());
        // backing
        assertTrue(keySet.isEmpty());
        assertTrue(valueSet.isEmpty());
        assertTrue(entrySet.isEmpty());
    }

    @Test
    public void keySetTest() {
        mapTest.keySet();
        assertThrows(UnsupportedOperationException.class, () -> {
            mapTest.keySet();
        });

        init();
        fill();

        HSet testSet = mapTest.keySet();
        HIterator testIter = testSet.iterator();

        while (testIter.hasNext())
            assertTrue(mapTest.containsKey(testIter.next()));
    }

    @Test
    public void valuesTest() {
        mapTest.values();
        assertThrows(UnsupportedOperationException.class, () -> {
            mapTest.values();
        });
        init();
        fill();

        HCollection testSet = mapTest.values();
        HIterator testIter = testSet.iterator();

        while (testIter.hasNext())
            assertTrue(mapTest.containsValue(testIter.next()));
    }

    @Test
    public void entrySetTest() {
        mapTest.entrySet();
        assertThrows(UnsupportedOperationException.class, () -> {
            mapTest.entrySet();
        });
    }

    @Test
    public void equalsTest() {
        fill();

        MapAdapter myMap = new MapAdapter();
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            myMap.put(i, tmp);
        }
        assertTrue(mapTest.equals(myMap));

        myMap = new MapAdapter(mapTest);
        assertTrue(mapTest.equals(myMap));
    }

    @Test
    public void hashCodeTest() {
        fill();

        int myHashCode = 0;
        HIterator myIter = mapTest.entrySet().iterator();
        while (myIter.hasNext())
            myHashCode += myIter.next().hashCode();

        init();
        fill();

        assertEquals(myHashCode, mapTest.hashCode());

        MapAdapter myMap = new MapAdapter(mapTest);
        assertEquals(myMap.hashCode(), mapTest.hashCode());
    }

    // IteratorAdapter Test
    public void createIterator() {
        entrySet = mapTest.entrySet();
        entryIter = entrySet.iterator();
        valueSet = mapTest.values();
        valueIter = valueSet.iterator();
        keySet = mapTest.keySet();
        keyIter = keySet.iterator();
    }

    @Test
    public void hasNextTest() {
        mapTest = new MapAdapter();

        fill();
        createIterator();

        for (int i = 0; i < 20; i++) {
            assertTrue(entryIter.hasNext());
            entryIter.next();

            assertTrue(valueIter.hasNext());
            valueIter.next();

            assertTrue(keyIter.hasNext());
            keyIter.next();
        }

        assertFalse(entryIter.hasNext());

        assertFalse(valueIter.hasNext());

        assertFalse(keyIter.hasNext());
    }

    @Test
    public void nextTest() {
        fill();

        createIterator();

        int i = 19;

        while (valueIter.hasNext() && keyIter.hasNext()) {
            String tmp = "valore n. " + i;
            assertEquals(tmp, valueIter.next());
            assertEquals(i, keyIter.next());
            i--;
            entryIter.next();
        }

        assertThrows(NoSuchElementException.class, () -> {
            valueIter.next();
        });

        assertThrows(NoSuchElementException.class, () -> {
            keyIter.next();
        });

        assertThrows(NoSuchElementException.class, () -> {
            entryIter.next();
        });
    }

    @Test
    public void iterRemoveTest() {
        fill();
        createIterator();

        assertThrows(IllegalStateException.class, () -> {
            entryIter.remove();
        });

        assertThrows(IllegalStateException.class, () -> {
            valueIter.remove();
        });

        assertThrows(IllegalStateException.class, () -> {
            keyIter.remove();
        });

        // key iterator remove and backing test
        while (keyIter.hasNext())
            keyIter.next();

        assertFalse(mapTest.isEmpty());

        for (int i = 0; i < 20; i++)
            keyIter.remove();

        assertTrue(keySet.isEmpty());
        assertTrue(mapTest.isEmpty());
        assertTrue(entrySet.isEmpty());
        assertTrue(valueSet.isEmpty());

        // value iterator remove and backing test
        init();
        fill();
        createIterator();
        while (valueIter.hasNext())
            valueIter.next();

        assertFalse(mapTest.isEmpty());

        for (int i = 0; i < 20; i++)
            valueIter.remove();

        assertTrue(valueSet.isEmpty());
        assertTrue(mapTest.isEmpty());
        assertTrue(entrySet.isEmpty());
        assertTrue(keySet.isEmpty());

        // entry iterator remove and backing test
        init();
        fill();
        createIterator();
        while (entryIter.hasNext())
            entryIter.next();

        assertFalse(mapTest.isEmpty());

        for (int i = 0; i < 20; i++)
            entryIter.remove();

        assertTrue(entrySet.isEmpty());
        assertTrue(mapTest.isEmpty());
        assertTrue(valueSet.isEmpty());
        assertTrue(keySet.isEmpty());
    }

    @Test
    public void setSizeTest() {
        fill();
        createIterator();
        assertEquals(entrySet.size(), 20);
        assertEquals(keySet.size(), 20);
        assertEquals(valueSet.size(), 20);
    }

    @Test
    public void setIsEmptyTest() {
        createIterator();
        assertEquals(entrySet.size(), 0);
        assertEquals(keySet.size(), 0);
        assertEquals(valueSet.size(), 0);
    }

    @Test
    public void setContainsTest() {
        fill();
        createIterator();
        assertThrows(NullPointerException.class, () -> {
            entrySet.contains(null);
        });
        assertThrows(NullPointerException.class, () -> {
            keySet.contains(null);
        });
        assertThrows(NullPointerException.class, () -> {
            valueSet.contains(null);
        });

        assertTrue(keySet.contains(1));
        assertTrue(valueSet.contains("valore n. " + 19));
        assertFalse(keySet.contains(20));
        assertFalse(valueSet.contains("valore n. " + 30));
    }

    @Test
    public void setToArrayTest() {
        fill();
        createIterator();

        Object[] myVArr = new Object[20];
        Object[] myKArr = new Object[20];

        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            myVArr[19 - i] = "valore n. " + i;
            myKArr[19 - i] = i;
        }

        assertThrows(NullPointerException.class, () -> {
            valueSet.toArray(null);
        });
        assertThrows(NullPointerException.class, () -> {
            keySet.toArray(null);
        });
        assertThrows(NullPointerException.class, () -> {
            entrySet.toArray(null);
        });

        Object[] ValArr = valueSet.toArray();
        Object[] EntryArr = entrySet.toArray();
        Object[] KeyArr = keySet.toArray();

        assertEquals(ValArr, myVArr);
        assertEquals(KeyArr, myKArr);
    }

    @Test
    public void setRemoveTest() {
        fill();
        createIterator();

        assertThrows(NullPointerException.class, () -> {
            entrySet.remove(null);
        });

        assertThrows(NullPointerException.class, () -> {
            valueSet.remove(null);
        });

        assertThrows(NullPointerException.class, () -> {
            keySet.remove(null);
        });

        // key set remove and backing test
        for (int i = 0; i < 20; i++)
            assertTrue(keySet.remove(i));

        assertTrue(keySet.isEmpty());
        assertTrue(mapTest.isEmpty());
        assertTrue(entrySet.isEmpty());
        assertTrue(valueSet.isEmpty());

        // value set remove and backing test
        init();
        fill();
        createIterator();

        for (int i = 0; i < 20; i++)
            assertTrue(valueSet.remove("valore n. " + i));

        assertTrue(valueSet.isEmpty());
        assertTrue(mapTest.isEmpty());
        assertTrue(entrySet.isEmpty());
        assertTrue(keySet.isEmpty());
    }

    @Test
    public void setContainsAllTest() {
        createIterator();
        assertThrows(NullPointerException.class, () -> {
            keySet.containsAll(null);
            entrySet.containsAll(null);
            valueSet.containsAll(null);
        });
    }

    @Test
    public void setAddAll() {
        createIterator();
        assertThrows(UnsupportedOperationException.class, () -> {
            keySet.add("prova");
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            valueSet.add("prova");
        });
        // cant make any other test cuz with only MapAdapter calss public, we can make
        // SetAdapters only through MapAdapter.TypeSet(), but since this, add() and
        // addAll() are no more allowed (UnsupportedOperationEcxception)
    }

    @Test
    public void setRetainAllTest() {
        createIterator();
        assertThrows(NullPointerException.class, () -> {
            entrySet.retainAll(null);
        });
        assertThrows(NullPointerException.class, () -> {
            keySet.retainAll(null);
        });
        assertThrows(NullPointerException.class, () -> {
            valueSet.retainAll(null);
        });
        // COME LO FACCIO?
    }

    @Test
    public void setRemoveAllTest() {
        createIterator();
        assertThrows(NullPointerException.class, () -> {
            entrySet.retainAll(null);
        });
        assertThrows(NullPointerException.class, () -> {
            keySet.retainAll(null);
        });
        assertThrows(NullPointerException.class, () -> {
            valueSet.retainAll(null);
        });
        // COME STRACAZZO LO FACCIO?
    }

    @Test
    public void setClearTest() {
        fill();
        createIterator();

        assertFalse(mapTest.isEmpty());
        // backing
        assertFalse(keySet.isEmpty());
        assertFalse(valueSet.isEmpty());
        assertFalse(entrySet.isEmpty());

        keySet.clear();

        assertTrue(mapTest.isEmpty());
        // backing
        assertTrue(keySet.isEmpty());
        assertTrue(valueSet.isEmpty());
        assertTrue(entrySet.isEmpty());

        init();
        fill();
        createIterator();

        valueSet.clear();

        assertTrue(mapTest.isEmpty());
        // backing
        assertTrue(keySet.isEmpty());
        assertTrue(valueSet.isEmpty());
        assertTrue(entrySet.isEmpty());

        entrySet.clear();

        assertTrue(mapTest.isEmpty());
        // backing
        assertTrue(keySet.isEmpty());
        assertTrue(valueSet.isEmpty());
        assertTrue(entrySet.isEmpty());
    }

    // EntryAdapter test
    @Test
    public void entryEqualsTest() {
        fill();
        createIterator();

        MapAdapter myMap = new MapAdapter();

        // fil
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            myMap.put(i, tmp);
        }

        // set
        HSet myEntrySet = myMap.entrySet();
        HIterator myEntryIterator = myEntrySet.iterator();

        while (myEntryIterator.hasNext())
            assertTrue(myEntryIterator.next().equals(entryIter.next()));
    }

    @Test
    public void entryHashcodeTest() {
        createIterator();
        assertEquals(mapTest.hashCode(), 0);
    }
}
