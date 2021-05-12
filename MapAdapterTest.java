package myTest;

import myAdapter.*;
import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.Before;

public class MapAdapterTest {

    MapAdapter mapTest;
    //Set
    HSet entrySet;
    HSet keySet;
    HCollection valueSet;
    //Iterators
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
        //MANCA IL BACKING
    }

    @Test
    public void removeTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.remove(null);
        });
        assertEquals(null, mapTest.remove("chiave"));
        mapTest.put("chiave", "valore");
        assertEquals("valore", mapTest.remove("chiave"));
        //MANCA IL BACKING
    }

    @Test
    public void putAllTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.putAll(null);
        });

        fill();

        MapAdapter myMap = new MapAdapter();
        myMap.putAll(mapTest);

        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            assertEquals(tmp, myMap.get(i));
        }
        //MANCA IL BACKING
    }

    @Test
    public void clearTest() {
        fill();

        mapTest.clear();

        assertTrue(mapTest.isEmpty());
        //MANCA IL BACKING
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
        //COME FACCIO A VERIFICARE UN SET... DI CUI NON HO I METODI??
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

        assertTrue(mapTest.isEmpty());

        // value iterator remove and backing test
        init();
        fill();
        createIterator();
        while (valueIter.hasNext())
            valueIter.next();

        assertFalse(mapTest.isEmpty());

        for (int i = 0; i < 20; i++)
            valueIter.remove();

        assertTrue(mapTest.isEmpty());

        // entry iterator remove and backing test
        init();
        fill();
        createIterator();
        while (entryIter.hasNext())
            entryIter.next();

        assertFalse(mapTest.isEmpty());

        for (int i = 0; i < 20; i++)
            entryIter.remove();

        assertTrue(mapTest.isEmpty());

    }

}
