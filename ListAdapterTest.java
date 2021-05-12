package myTest;

import myAdapter.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import org.junit.Before;

public class ListAdapterTest {

    ListAdapter listTest;

    @Before
    public void init(){
        listTest = new ListAdapter();
    }

    @Test
    public void sizeTest(){
        assertEquals(listTest.size(), 0);
    }

    @Test
    public void isEmptyTest(){
        assertTrue(listTest.isEmpty() == true);
    }

    @Test
    public void containsTest(){
        assertThrows(NullPointerException.class, () -> { listTest.contains(null); });
        listTest.add(5);
        assertTrue(listTest.contains(5));
    }

    @Test
    public void toArrayTest()
    {
        Object[] a = new Object[20];
        for(int i = 0; i < 20; i++) {
            a[i] = i * 10;
            listTest.add(i*10);
        }
        Object[] a1 = listTest.toArray();
        assertEquals(a,a1);
    }

    @Test
    public void toArrayTest2()
    {
        assertThrows(NullPointerException.class, () -> { listTest.toArray(null); });

        Object[] a1 = new Object[20];
        Object[] a = new Object[20];
    }

    @Test
    public void addTest()
    {
        assertThrows(NullPointerException.class, () -> { listTest.add(null); });
        assertTrue(listTest.add(5));
    }

    @Test
    public void removeTest1()
    {
        assertFalse(listTest.remove((Integer)5));
        listTest.add(5);
        assertTrue(listTest.remove((Integer)5));
        assertFalse(listTest.contains(5));
    }

    @Test
    public void containsAllTest()
    {
        assertThrows(NullPointerException.class, () -> { listTest.containsAll(null); });
        ListAdapter L = new ListAdapter();
        for(int i = 0; i < 20; i++) {
            L.add(i * 10);
            listTest.add(i*10);
        }
        assertTrue(listTest.containsAll(L));
    }

    @Test
    public void addAllTest1()
    {
        ListAdapter L = new ListAdapter();
        assertFalse(listTest.addAll(null));
        for(int i = 0; i < 20; i++)
            L.add(i * 10);
        listTest.addAll(L);
        for(int i = 0; i < 20; i++) {
            assertEquals(i * 10, listTest.get(i));
        }
    }

    @Test
    public void addAllTest2()
    {
        ListAdapter L = new ListAdapter();
        assertFalse(listTest.addAll(null));
        for(int i = 0; i < 20; i++)
            L.add(i * 10);
        listTest.addAll(L);


    }

    @Test
    public void removeAllTest()
    {
        ListAdapter L = new ListAdapter();
        assertFalse(listTest.addAll(null));
        for(int i = 0; i < 20; i++)
        {
            L.add(i * 10);
        }


        assertThrows(NullPointerException.class, () -> { listTest.removeAll(null); });

    }

    @Test
    public void retainAllTest()
    {
        assertThrows(NullPointerException.class, () -> { listTest.retainAll(null); });

    }

    @Test
    public void clearTest()
    {
        for(int i = 0; i < 20; i++)
            listTest.add(i * 10);

        ListAdapter L = new ListAdapter();
        listTest.clear();
        assertEquals(L,listTest);
    }

    @Test
    public void equalsTest()
    {
        ListAdapter L1 = new ListAdapter();
        ListAdapter L2 = new ListAdapter();

        fillList();

        for(int i = 0; i < 20; i++)
        {
            L1.add(i * 10);
            L2.add((20-i)*10);
        }

        assertTrue(listTest.equals(L1));
        assertFalse(listTest.equals(L2));
    }

    @Test
    public void hashCodeTest()
    {
        fillList();

        int hashCode = 1;
        HIterator iter = listTest.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
        }

        assertEquals(hashCode,listTest.hashCode());
    }

    @Test
    public void getTest()
    {
        fillList();

        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.get(-1); });
        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.get(25); });

        assertEquals(20,listTest.get(2));
    }

    @Test
    public void setTest()
    {
        fillList();

        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.set(-1, 5); });
        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.set(25, 5); });

        assertThrows(NullPointerException.class, () -> { listTest.set(5, null); });

        assertEquals(20,listTest.set(2,120));
        assertEquals(120,listTest.get(2));
    }

    @Test
    public void addTest2()
    {
        fillList();

        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.add(-1,5); });
        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.add(25,5); });

        listTest.add(2,120);
        assertEquals(120, listTest.get(2));
    }

    @Test
    public void removeTest2()
    {
        fillList();

        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.remove(-1); });
        assertThrows(IndexOutOfBoundsException.class, () -> { listTest.remove(25); });

        assertEquals(20, listTest.get(2));
        assertEquals(20, listTest.remove(2));
        assertEquals(30, listTest.remove(2));
    }

    @Test
    public void indexOfTest()
    {
        fillList();
        for(int i = 10; i > 0; i--)
            listTest.add(i*10);

        assertThrows(NullPointerException.class, () -> { listTest.indexOf(null); });

        assertEquals(2, listTest.indexOf(20));
    }

    @Test
    public void lastIndexOfTest()
    {
        fillList();
        for(int i = 10; i > 0; i--)
            listTest.add(i*10);

        assertThrows(NullPointerException.class, () -> { listTest.lastIndexOf(null); });

        assertEquals(18, listTest.lastIndexOf(20));
    }

    public void fillList()
    {
        for(int i = 0; i < 20; i ++)
            listTest.add(i * 10);
    }
}