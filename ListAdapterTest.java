package myTest;

import myAdapter.*;
import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;
import org.junit.Before;

public class ListAdapterTest {

    ListAdapter listTest;
    HListIterator listIter;
    HIterator iterTest;

    @Before
    public void init() {
        listTest = new ListAdapter();
    }

    @Test
    public void sizeTest() {
        assertEquals(listTest.size(), 0);
    }

    @Test
    public void isEmptyTest() {
        assertTrue(listTest.isEmpty());
    }

    @Test
    public void containsTest() {
        assertThrows(NullPointerException.class, () -> {
            listTest.contains(null);
        });
        listTest.add("test");
        listTest.add("test2");
        listTest.add("test3");
        assertTrue(listTest.contains("test"));
    }

    @Test
    public void toArrayTest() {
        Object[] a = new Object[20];
        for (int i = 0; i < 20; i++) {
            a[i] = "valore n. " + i;
            listTest.add("valore n. " + i);
        }
        Object[] a1 = listTest.toArray();
        assertEquals(a, a1);
    }

    @Test
    public void addTest() {
        assertThrows(NullPointerException.class, () -> {
            listTest.add(null);
        });
        assertTrue(listTest.add("test"));
    }

    @Test
    public void removeTest1() {
        assertThrows(NullPointerException.class, () -> {
            listTest.remove(null);
        });
        assertFalse(listTest.remove("test"));
        listTest.add("test");
        assertTrue(listTest.remove("test"));
        assertFalse(listTest.contains("test"));
    }

    @Test
    public void containsAllTest() {
        assertThrows(NullPointerException.class, () -> {
            listTest.containsAll(null);
        });
        ListAdapter myList = new ListAdapter();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0)
                myList.add("valore n. " + i);
            listTest.add("valore n. " + i);
        }
        assertTrue(listTest.containsAll(myList));

        // "opposite" check
        init();
        myList = new ListAdapter();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0)
                listTest.add("valore n. " + i);
            myList.add("valore n. " + i);
        }
        assertFalse(listTest.containsAll(myList));
    }

    @Test
    public void addAllTest1() {
        assertThrows(NullPointerException.class, () -> {
            listTest.addAll(null);
        });

        ListAdapter myList = new ListAdapter();
        assertFalse(listTest.addAll(myList));

        for (int i = 0; i < 20; i++)
            myList.add("valore n. " + i);

        listTest.addAll(myList);
        for (int i = 0; i < 20; i++) {
            assertEquals("valore n. " + i, listTest.get(i));
        }
    }

    @Test
    public void addAllTest2() {
        assertThrows(NullPointerException.class, () -> {
            listTest.addAll(null);
        });
        ListAdapter myList = new ListAdapter();
        assertFalse(listTest.addAll(myList));

        for (int i = 0; i < 10; i++)
            listTest.add("valore n. " + i);

        for (int i = 10; i < 20; i++)
            myList.add("valore n. " + i);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.addAll(12, myList);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.addAll(-1, myList);
        });

        listTest.addAll(10, myList);
    }

    @Test
    public void removeAllTest() {
        ListAdapter myList = new ListAdapter();

        assertThrows(NullPointerException.class, () -> {
            listTest.removeAll(null);
        });

        assertFalse(listTest.addAll(myList));

        for (int i = 0; i < 20; i++) {
            listTest.add("valore n. " + i);
        }

        for (int i = 0; i < 20; i++) {
            myList.add("n. " + i);
        }

        assertFalse(listTest.removeAll(myList));

        myList.add("valore n. " + 1);

        assertTrue(listTest.removeAll(myList));

        init();
        ListAdapter evenList = new ListAdapter();
        ListAdapter oddList = new ListAdapter();

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        assertFalse(listTest.retainAll(evenList));

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0)
                evenList.add("valore n. " + i);
            else
                oddList.add("valore n. " + i);
        }

        assertTrue(listTest.removeAll(evenList));

        assertTrue(listTest.equals(oddList));
    }

    @Test
    public void retainAllTest() {
        assertThrows(NullPointerException.class, () -> {
            listTest.retainAll(null);
        });

        ListAdapter evenList = new ListAdapter();

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        assertFalse(listTest.retainAll(evenList));

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0)
                evenList.add("valore n. " + i);
        }

        assertTrue(listTest.retainAll(evenList));

        assertTrue(listTest.equals(evenList));

    }

    @Test
    public void clearTest() {

        for (int i = 0; i < 20; i++)
            listTest.add(i * 10);

        listTest.clear();
        assertEquals(listTest.size(), 0);
    }

    @Test
    public void equalsTest() {
        init();
        ListAdapter evenList = new ListAdapter();
        ListAdapter fullList = new ListAdapter();

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0)
                evenList.add("valore n. " + i);

            fullList.add("valore n. " + i);
            listTest.add("valore n. " + i);
        }

        assertTrue(listTest.equals(fullList));
        assertFalse(listTest.equals(evenList));
    }

    @Test
    public void hashCodeTest() {
        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        int hashCode = 1;
        HIterator iter = listTest.iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        }

        assertEquals(hashCode, listTest.hashCode());
    }

    @Test
    public void getTest() {
        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.get(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.get(25);
        });

        assertEquals("valore n. 1", listTest.get(1));
    }

    @Test
    public void setTest() {
        for (int i = 0; i < 20; i++) {
            listTest.add("valore n. " + i);
        }

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.set(-1, 5);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.set(25, 5);
        });

        assertThrows(NullPointerException.class, () -> {
            listTest.set(5, null);
        });

        assertEquals("valore n. 2", listTest.set(2, "test"));
        assertEquals("test", listTest.get(2));
    }

    @Test
    public void secondAddTest() {

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.add(-1, 5);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.add(25, 5);
        });

        listTest.add(0, "test");

        assertEquals("test", listTest.get(0));
        assertNotEquals("test!", listTest.get(0));
    }

    @Test
    public void secondRemoveTest() {
        for (int i = 0; i < 20; i++) {
            listTest.add("valore n. " + i);
        }

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.remove(-1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.remove(25);
        });

        assertEquals("valore n. 2", listTest.remove(2));
        assertEquals("valore n. 3", listTest.remove(2));
    }

    @Test
    public void indexOfTest() {
        assertThrows(NullPointerException.class, () -> {
            listTest.indexOf(null);
        });

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        assertEquals(2, listTest.indexOf("valore n. 2"));
    }

    @Test
    public void lastIndexOfTest() {

        assertThrows(NullPointerException.class, () -> {
            listTest.lastIndexOf(null);
        });

        for (int i = 0; i < 20; i++)
            listTest.add("test");

        assertEquals(19, listTest.lastIndexOf("test"));
    }

    @Test
    public void firstIistIteratorTest() {

        HListIterator iter = listTest.listIterator();
        assertFalse(iter.hasNext());

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        while (iter.hasNext())
            iter.next();

        assertFalse(iter.hasNext());

        assertEquals(listTest.size(), 20);
    }

    @Test
    public void secondListIterator() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.listIterator(2);
        });

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        HListIterator iterator = listTest.listIterator();

        assertTrue(iterator.hasNext());

    }

    @Test
    public void subListTest() {
        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.subList(-1, 0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.subList(0, 101);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.subList(7, 3);
        });

        HList sub = listTest.subList(10, 90);
        assertEquals(80, sub.size());

        HList sub2 = sub.subList(10, 70);
        assertEquals(60, sub2.size());

        HList sub3 = sub2.subList(10, 50);
        assertEquals(40, sub3.size());

        HList sub4 = sub3.subList(10, 30);
        assertEquals(20, sub4.size());

        assertThrows(UnsupportedOperationException.class, () -> {
            listTest.subList(3, 5);
        });

        assertTrue(sub.containsAll(sub2));
        assertTrue(sub2.containsAll(sub3));
        assertTrue(sub3.containsAll(sub4));
    }

    public void craeateIter() {
        iterTest = listTest.iterator();
        listIter = listTest.listIterator();
    }

    @Test
    public void iterHasNextTest() {
        craeateIter();

        assertFalse(iterTest.hasNext());

        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);

        assertTrue(iterTest.hasNext());

        while (iterTest.hasNext() && listIter.hasNext())
            iterTest.next();

        assertFalse(iterTest.hasNext());

    }

    @Test
    public void iterNextTest() {
        craeateIter();

        assertThrows(NoSuchElementException.class, () -> {
            iterTest.next();
        });

        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);

        int i = 0;

        while (iterTest.hasNext()) {
            assertEquals("valore n. " + i, iterTest.next());
            i++;
        }
    }

    @Test
    public void iterRemoveTest() {
        craeateIter();

        assertThrows(IllegalStateException.class, () -> {
            iterTest.remove();
        });

        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);

        assertThrows(IllegalStateException.class, () -> {
            iterTest.remove();
        });

        iterTest.next();
        iterTest.remove();

        assertThrows(IllegalStateException.class, () -> {
            iterTest.remove();
        });

        int counter = 0;
        while (iterTest.hasNext()) {
            iterTest.next();
            counter++;
        }

        assertEquals(listTest.size(), counter);
    }

    @Test
    public void listAddTest() {
        craeateIter();
        listIter.add("test");
        assertTrue(listIter.hasNext());
        assertEquals("test", listIter.next());
    }

    @Test
    public void listHasNextTest() {
        craeateIter();
        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);
        int counter = 0;
        while (listIter.hasNext()) {
            counter++;
            listIter.next();
        }
        assertEquals(counter, listTest.size());

        assertFalse(listIter.hasNext());
    }

    @Test
    public void iterHasPreviousTest() {
        craeateIter();
        assertFalse(listIter.hasPrevious());

        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);

        while (listIter.hasNext())
            listIter.next();

        assertTrue(listIter.hasPrevious());
    }

    @Test
    public void listNextTest() {
        craeateIter();

        assertThrows(NoSuchElementException.class, () -> {
            listIter.next();
        });

        for (int i = 0; i < 100; i++)
            listIter.add("valore n. " + i);

        int i = 99;

        while (listIter.hasNext()) {
            assertEquals("valore n. " + i, listIter.next());
            i--;
        }
    }

    @Test
    public void listNextIndexTest() {
        craeateIter();

        for (int i = 0; i < 100; i++)
            listIter.add("valore n. " + i);

        assertEquals(0, listIter.nextIndex());

        while (listIter.hasNext())
            listIter.next();

        for (int i = 0; i < 10; i++)
            assertEquals(99, listIter.nextIndex());
    }

    @Test
    public void listPreviousTest() {
        craeateIter();
        assertThrows(NoSuchElementException.class, () -> {
            listIter.previous();
        });

        for (int i = 0; i < 100; i++)
            listIter.add("valore n. " + i);

        while (listIter.hasNext())
            listIter.next();

        int i = 0;
        while (listIter.hasPrevious()) {
            assertEquals("valore n. " + i, listIter.previous());
            i++;
        }
    }

    @Test
    public void listPreviousIndexTest() {
        craeateIter();

        for (int i = 0; i < 100; i++)
            listIter.add("valore n. " + i);

        assertEquals(-1, listIter.previousIndex());

        while (listIter.hasNext())
            listIter.next();
        int i = 98;
        while (listIter.hasPrevious()) {
            assertEquals(i, listIter.previousIndex());
            listIter.previous();
            i--;
        }
    }

    @Test
    public void listRemoveTest() {
        craeateIter();

        for (int i = 0; i < 100; i++)
            listIter.add("valore n. " + i);

        assertThrows(IllegalStateException.class, () -> {
            listIter.remove();
        });

        while (listIter.hasNext())
            listIter.next();

        listIter.remove();
        assertThrows(IllegalStateException.class, () -> {
            listIter.remove();
        });

        assertFalse(listTest.contains("valore n. 0"));
    }

    @Test
    public void listSetTest() {
        craeateIter();

        for (int i = 0; i < 100; i++)
            listIter.add("valore n. " + i);

        assertThrows(IllegalStateException.class, () -> {
            listIter.remove();
        });

        while (listIter.hasNext())
            listIter.next();

        assertFalse(listTest.contains("test"));
        listIter.set("test");
        assertTrue(listTest.contains("test"));
    }
}