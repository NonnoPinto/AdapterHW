package myTest;

import myAdapter.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class MapAdapterTest {

    MapAdapter mapTest;

    @Before
    public void init() {
        mapTest = new MapAdapter();
    }

    @Test
    public void sizeTest() {
        assertEquals(mapTest.size(), 0);
    }

    @Test
    public void emptyTest() {
        assertTrue(mapTest.isEmpty());
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
    }

    @Test
    public void removeTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.remove(null);
        });
        assertEquals(null, mapTest.remove("chiave"));
        mapTest.put("chiave", "valore");
        assertEquals("valore", mapTest.remove("chiave"));
    }

    @Test
    public void putAllTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.putAll(null);
        });

        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            mapTest.put(i, tmp);
        }

        MapAdapter myMap = new MapAdapter();
        myMap.putAll(mapTest);

        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            assertEquals(tmp, myMap.get(i));
        }
    }

    @Test
    public void clearTest() {
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            mapTest.put(i, tmp);
        }
        mapTest.clear();

        assertTrue(mapTest.isEmpty());
    }

    // MANCANO TUTTI I TEST DEI SET, QUI IN MEZZO

    @Test
    public void eqaulsTest() {
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            mapTest.put(i, tmp);
        }

        MapAdapter myMap = new MapAdapter();
        for (int i = 0; i < 20; i++) {
            String tmp = "valore n. " + i;
            myMap.put(i, tmp);
        }

        assertTrue(mapTest.equals(myMap));
    }
}
