package myTest;

import myAdapter.*;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.graalvm.compiler.nodes.EntryMarkerNode;
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
    /**
     * <b>Method</b> : size()</br>
     *
     * <b>Summary</b> : check the size of the map. Fill the map with entries and
     * test size equals to number of entries</br>
     *
     * <b>Design</b> : this test has only two cases: empty map or not empty. Both
     * are tested </br>
     *
     * <b>Test Description</b> : size is checked after initialization (equals 0) and
     * after filling map (equals 20)</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : two different sizes, 0 and number of entries</br>
     *
     */
    @Test
    public void sizeTest() {
        assertEquals(mapTest.size(), 0);
        fill();
        assertEquals(mapTest.size(), 20);
    }

    /**
     * <b>Method</b> isEmpty() </br>
     *
     * <b>Summary</b> : test the presence of entries. If any, returns false, true
     * otherwise</br>
     *
     * <b>Design</b> : test an often used method, a quick way to test the presence
     * of any entry. Both chances are tested</br>
     *
     * <b>Description</b> : isEmpty is tested with a just initialized map and after
     * being filled with 20 entries</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : true if map has no entry, false otherwise</br>
     *
     */
    @Test
    public void emptyTest() {
        assertTrue(mapTest.isEmpty());
        fill();
        assertFalse(mapTest.isEmpty());
    }

    /**
     * <b>Method</b> containsKey(Object)</br>
     *
     * <b>Summary</b> : with an empty map the error is thrown. Then, we put one
     * entry and check if method sees it. Last, if method return false with a wrong
     * key</br>
     *
     * <b>Design</b> : test the presence (and the absence) of a key in a key-value
     * map. It has three chances: null, present, absent. All are tested</br>
     *
     * <b>Test Description</b> : with a null key, an existing key and a false key,
     * the test runs thorght three different results</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : true if key is found, false otherwise. Throwing
     * right exception when called</br>
     *
     */
    @Test
    public void containsKeyTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.containsKey(null);
        });
        mapTest.put("chiave", "valore");
        assertTrue(mapTest.containsKey("chiave"));
        assertFalse(mapTest.containsKey("chiave test"));
    }

    /**
     * <b>Method</b> containsValue(Object)</br>
     *
     * <b>Summary</b> : with an empty map the error is thrown. Then, we put one
     * entry and check if method sees it. Last, if method return false with a wrong
     * value</br>
     *
     * <b>Design</b> : test the presence (and the absence) of a value in a key-value
     * map. It has three chances: null, present, absent. All are tested</br>
     *
     * <b>Test Description</b> : with a null value, an existing and a false one, the
     * test runs thorght three different results</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : true if key is found, false otherwise. Throwing
     * right exception when called</br>
     *
     */
    @Test
    public void containsValueTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.containsValue(null);
        });
        mapTest.put("chiave", "valore");
        assertFalse(mapTest.containsValue("valore test"));
        assertTrue(mapTest.containsValue("valore"));
    }

    /**
     * <b>Method</b> get(Object)</br>
     *
     * <b>Summary</b> : with an empty map the error is thrown. Then, we put one
     * entry and check if method sees it</br>
     *
     * <b>Design</b> : test the presence of a key in a key-value map, returning
     * mapped value. It has three chances: null, present, absent. All are
     * tested</br>
     *
     * <b>Test Description</b> : with a null key and an existing one, the test
     * search and find the right value</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : value if key is found, null otherwise. Throwing
     * right exception when called</br>
     */
    @Test
    public void getTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.get(null);
        });
        mapTest.put("chiave", "valore");
        assertEquals("valore", mapTest.get("chiave"));
    }

    /**
     * <b>Method</b> put(Object,Object)</br>
     *
     * <b>Summary</b> : with an empty key-value errors are thrown. Then, we put one
     * entry, check the null return. The mapped value is changed (and returned).
     * Lastly, we search for the new value. For backing, map is initialized again
     * and filled. We alsoe create set for keys, values and entries and every one
     * with an iterator. Map is changed with some put() and results are searched in
     * every set.</br>
     * 
     * <b>Design</b> : testing different way of put, as a new entry or as a change
     * of a value, inside the mape and, thaks to backing, also in every set</br>
     *
     * <b>Test Description</b> : with a null key-value and an existingone, test runs
     * thorght different results. Then map is modified and set are checked to verify
     * backing</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : map has to have inserted entries</br>
     *
     * <b>Exptected results</b> : returning right value for an already exinting key.
     * Find map-inserted key-vale inside key, entry and value sets. Throwing right
     * exception when called</br>
     */
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

        mapTest.put(2, "sono diverso");
        mapTest.put(4, "papertino");
        mapTest.put(6, "pluto");
        assertEquals(mapTest.size(), keySet.size());
        assertEquals(mapTest.size(), valueSet.size());
        assertEquals(mapTest.size(), entrySet.size());

    }

    /**
     * <b>Method</b> remove(Object)</br>
     *
     * <b>Summary</b> : with an empty key errors are thrown. Then, wetry to remove a
     * non exiting key and an exiting one, returning null and mapped value.</br>
     * 
     * <b>Design</b> : test wants to verify how removing entries works, on the map
     * and on backed sets</br>
     *
     * <b>Test Description</b> : test calls error-situations. Then it removes object
     * from the map, to check return and backing in every set</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : removed entries no more inside the map</br>
     *
     * <b>Exptected results</b> : returning right value for an already exinting key.
     * Find map-inserted key-vale inside key, entry and value sets. Throwing right
     * exception when called</br>
     */
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

    /**
     * <b>Method</b> putAll(HCollection)</br>
     *
     * <b>Summary</b> : with an empty map error is thrown. Map is filled, set and
     * iterators are made. After mathod call, we search for equality between two
     * maps and in sets</br>
     * 
     * <b>Design</b> : test wants to verify how removing entries works, on the map
     * and on backed sets</br>
     *
     * <b>Test Description</b> : test calls error-situations. Then it removes object
     * from the map, to check return and backing in every set</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : filled made has to be contained in testMap (and in
     * every set)</br>
     *
     * <b>Exptected results</b> : finding all myMap values inside testMap and sets.
     * Throwing right exception when called</br>
     */
    @Test
    public void putAllTest() {
        assertThrows(NullPointerException.class, () -> {
            mapTest.putAll(null);
        });

        createIterator();

        fill();

        MapAdapter myMap = new MapAdapter();

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

    /**
     * <b>Method</b> clear()</br>
     *
     * <b>Summary</b> : map is filled and checked if its notEmpty (same every set).
     * Calling clear method only on map, test checks if it is empty (and every
     * set)</br>
     *
     * <b>Design</b> : map is fille and cleaned. Set are only checked if they are
     * really backed by the map</br>
     *
     * <b>Test Description</b> : it checks if the clear() works as intended and
     * clean up map and sets</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : Map and sets needs to be empty</br>
     *
     * <b>Expected results</b> : Map and all sets empty. Throwing right exception
     * when called</br>
     *
     */
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

    /**
     * <b>Method</b> keySet()</br>
     *
     * <b>Summary</b> : we try to call method twice (not permitted). Later, we test
     * if all keySet are iside the map and if they have same size</br>
     *
     * <b>Design</b> : map is filled after creating set. This way, we check both
     * keySet creation and baacking map->set</br>
     *
     * <b>Test Description</b> : test checks if key set cant be made twice from same
     * map. Later, if key map has all keys</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized and alreday with one
     * keySet</br>
     *
     * <b>Post-condition</b> : Map and key set has same keys</br>
     *
     * <b>Expected results</b> : Map and key set with same keys. Throwing right
     * exception when called</br>
     *
     */
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
        assertEquals(mapTest.size(), testSet.size());
    }

    /**
     * <b>Method</b> values()</br>
     *
     * <b>Summary</b> : we try to call method twice (not permitted). Later, we test
     * if all values are iside the map and if they have same size</br>
     *
     * <b>Design</b> : map is filled after creating set. This way, we check both
     * keySet creation and baacking map->set</br>
     *
     * <b>Test Description</b> : test checks if key set cant be made twice from same
     * map. Later, if key map has all values</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized and alreday with one
     * valueSet</br>
     *
     * <b>Post-condition</b> : Map and value set has same values</br>
     *
     * <b>Expected results</b> : Map and value set with same values. Throwing right
     * exception when called</br>
     *
     */
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
        assertEquals(mapTest.size(), testSet.size());
    }

    /**
     * <b>Method</b> values()</br>
     *
     * <b>Summary</b> : we try to call method twice (not permitted). By cheking set
     * size before and after filling map, we check backing activity</br>
     *
     * <b>Design</b> : map is filled after creating set. This way, we check if
     * entrySet supports it and is backed</br>
     *
     * <b>Test Description</b> : test checks if entry set cant be made twice from
     * same map and if entrySet grows with the map</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized and alreday with one
     * entrySet</br>
     *
     * <b>Post-condition</b> : Map and value set has same values</br>
     *
     * <b>Expected results</b> : Map and value set with same values. Throwing right
     * exception when called</br>
     *
     */
    @Test
    public void entrySetTest() {
        HSet testSet = mapTest.entrySet();
        assertThrows(UnsupportedOperationException.class, () -> {
            mapTest.entrySet();
        });
        assertEquals(0, testSet.size());
        fill();
        assertEquals(mapTest.size(), testSet.size());
    }

    /**
     * <b>Method</b> equals()</br>
     *
     * <b>Summary</b> : testMap and myMap are filled the samt way, then
     * comapred</br>
     *
     * <b>Design</b> : two different maps are manually filled with same entries.
     * Then compared</br>
     *
     * <b>Test Description</b> : it checks if the equals method works as intended
     * and returns true if Object are the same, false otherwise</br>
     *
     * <b>Pre-conditions</b> : Both Maps have to be initialized and filled</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Expected results</b> : True if they are the same, false otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> hashCode()</br>
     *
     * <b>Summary</b> : map is filled and hashcode invoceted. Than hascode is
     * calcolated manuelly. Then compared each other</br>
     *
     * <b>Design</b> : test checks if hashCode method make the same hashCode as
     * given formula</br>
     *
     * <b>Test Description</b> : hashCode is manually mande and with method. Then
     * comapred</br>
     *
     * <b>Pre-conditions</b> : Map has to be initialized and filled</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Expected results</b> : True hashCode() is equals to "manual" hashcode,
     * false otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> hasNext()</br>
     *
     * <b>Summary</b> : test checks if hasNext return false when map is empty or
     * when it at its last position. The same test is made also fo every set</br>
     *
     * <b>Design</b> : test is build to test method with empty or full map </br>
     *
     * <b>Test Description</b> : hasNext is tested with empty map, iterating the map
     * and at the end of the map</br>
     *
     * <b>Pre-conditions</b> : Map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Expected results</b> : True if index is not on the last item, false
     * otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> next()</br>
     *
     * <b>Summary</b> : test checks if next throw exception empty or it at its last
     * position, the indexx++ element if hasNext()</br>
     *
     * <b>Design</b> : test is build to test method with empty or full map </br>
     *
     * <b>Test Description</b> : hasNext is tested iterating the map and at the end
     * of the map (throwing exception)</br>
     *
     * <b>Pre-conditions</b> : Map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Expected results</b> : elementAt(index++) if index is not on the last
     * item, exception otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> remove()</br>
     *
     * <b>Summary</b> : test, for easier understanding, is divided. First it check
     * if remove on the map throws excpetion when map is empty, it fills map and
     * empties it element by element. After initializing map again, it makes the
     * same test, but checking backing on sets</br>
     *
     * <b>Design</b> : test is divded in two parts: testing remove on maps, testing
     * remove on sets and chacking remove on maps</br>
     *
     * <b>Test Description</b> : first it tests remove on map (also excpetion), then
     * remove on sets and backing on maps</br>
     *
     * <b>Pre-conditions</b> : Map has to be initialized</br>
     *
     * <b>Post-condition</b> : Map is emptied by remove</br>
     *
     * <b>Expected results</b> : Map empty after calling remove() on every entry.
     * Throwing right exception when called</br>
     *
     */
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

    // SetAdapter Test
    /**
     * <b>Method</b> : size()</br>
     *
     * <b>Summary</b> : check the size of sets. Making sets befor filling to check
     * backing. Fill map with entries and test size equals to number of entries</br>
     *
     * <b>Design</b> : this test has only two cases: empty map or not empty. Both
     * are tested </br>
     *
     * <b>Test Description</b> : size is checked after after filling map (equals
     * 20)</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : two different sizes, 0 and number of entries</br>
     *
     */
    @Test
    public void setSizeTest() {
        createIterator();
        fill();
        assertEquals(entrySet.size(), 20);
        assertEquals(keySet.size(), 20);
        assertEquals(valueSet.size(), 20);
    }

    /**
     * <b>Method</b> : isEmpty()</br>
     *
     * <b>Summary</b> : check size==0 of sets. Making sets befor filling to check
     * backing.</br>
     *
     * <b>Design</b> : this test has only two cases: empty map or not empty. Both
     * are tested </br>
     *
     * <b>Test Description</b> : method isEmpty is checked with empty and filled
     * map</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : true if map is empty, false otherwise</br>
     *
     */
    @Test
    public void setIsEmptyTest() {
        createIterator();
        assertTrue(entrySet.isEmpty());
        assertTrue(keySet.isEmpty());
        assertTrue(valueSet.isEmpty());
        fill();
        assertFalse(entrySet.isEmpty());
        assertFalse(keySet.isEmpty());
        assertFalse(valueSet.isEmpty());
    }

    /**
     * <b>Method</b> : contains(Object)</br>
     *
     * <b>Summary</b> : first, test generates an exception, using null argoument.
     * Then it search for different key-value in map and sets</br>
     *
     * <b>Design</b> : test is based on searching existing and not existing entries.
     * </br>
     *
     * <b>Test Description</b> : test throws exception, then search for entries</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized and filled iwth some
     * values</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : true if map is entry in inside map, false
     * otherwise</br>
     *
     */
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
    
    /**
     * <b>Method</b> : to Array(Object[])</br>
     *
     * <b>Summary</b> : test create arrays in two ways: manually and by method. Then it checks if they are equals</br>
     *
     * <b>Design</b> : test is based on comparison between manully made array and method toArray</br>
     *
     * <b>Test Description</b> : test make array by method and manuall, then make a comaprison</br>
     *
     * <b>Pre-conditions</b> : map has to be initialized and filled iwth some
     * values</br>
     *
     * <b>Post-condition</b> : We have arrays from all sets</br>
     *
     * <b>Exptected results</b> : arrays with key, values and entries</br>
     *
     */
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
        assert
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
