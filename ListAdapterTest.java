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
    HList subList;

    @Before
    public void init() {
        listTest = new ListAdapter();
    }

    /**
     * <b>Method</b> size()<br>
     *
     * <b>Summary</b> : Size method test, check if the list size is 0.<br>
     *
     * <b>Design</b> : Check the size of list<br>
     *
     * <b>Test description</b> : Check if the list is void<br>
     *
     * <b>Pre-conditions</b> : The List is initialized.<br>
     *
     * <b>Post-condition</b> : N/A<br>
     *
     * <b>Exptected results</b> : true if the size of initialized list is 0, false
     * otherwise.<br>
     */
    @Test
    public void sizeTest() {
        assertEquals(listTest.size(), 0);
    }

    /**
     * <b>Method</b> isEmpty()<br>
     * 
     * <b>Summary</b> : Check if the list is null size.<br>
     * 
     * <b>Design</b> : return true if the list is empty.<br>
     * 
     * <b>Test description</b> : Check if initialized list is empty.<br>
     * 
     * <b>Pre-condition</b> :ListAdapter Object initialized, with size equal to
     * zero.<br>
     * 
     * <b>Post-conditions</b> : N/A<br>
     * 
     * <b>Expected results</b> : return true if the size list is zero.<br>
     */
    @Test
    public void isEmptyTest() {
        assertTrue(listTest.isEmpty());
    }

    /**
     * <b>Method<b> contains()
     * 
     * <b>Summary</b> : call contains with an object as parameter and make sure that
     * the method return true, false if the element is not on the list. <br>
     * 
     * <b>Design</b> : Check that some objects are in the list, one doesnt. <br>
     * 
     * <b>Test description</b> : Check if a null element generate NullPointer
     * exception, then add elements to the list and check if the list contains the
     * object. Searching for a non insterted object<br>
     * 
     * <b>Pre-conditions</b> : List initialized.<br>
     * 
     * <b>Post-condition</b> : N/A<br>
     * 
     * <b>Expected results</b> : true if argoument is inside list, false
     * otherwise.<br>
     */
    @Test
    public void containsTest() {
        assertThrows(NullPointerException.class, () -> {
            listTest.contains(null);
        });
        listTest.add("test");
        listTest.add("test2");
        listTest.add("test3");
        assertTrue(listTest.contains("test"));
        assertTrue(listTest.contains("test2"));
        assertTrue(listTest.contains("test3"));
        assertFalse(listTest.contains("test4"));
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
        assertTrue(listTest.contains("test"));

        // backing
        init();
        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);

        subList = listTest.subList(30, 40);
        int oldSize = subList.size();

        subList.add("test");
        assertTrue(listTest.contains("test"));
        assertEquals("test", listTest.get(40));

        assertTrue(oldSize < subList.size());
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

        // backing
        init();
        for (int i = 0; i < 100; i++)
            listTest.add("valore n. " + i);

        subList = listTest.subList(30, 40);
        int oldSize = subList.size();

        assertFalse(subList.remove("valore n. 1"));
        assertTrue(listTest.contains("valore n. 30"));
        assertTrue(subList.remove("valore n. 30"));
        assertFalse(listTest.contains("valore n. 30"));

        assertTrue(oldSize > subList.size());
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
        // backing
        init();
        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);
        subList = listTest.subList(10, 15);

        myList = new ListAdapter();
        for (int i = 21; i < 40; i++)
            myList.add("test n. " + i);

        subList.addAll(myList);
        assertTrue(listTest.containsAll(myList));
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
        HIterator iter = listTest.iterator();
        int k = 0;
        while (iter.hasNext()) {
            assertEquals("valore n. " + k, iter.next());
            k++;
        }

        // backing
        init();

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);
        subList = listTest.subList(10, 15);

        myList.clear();
        for (int i = 21; i < 40; i++)
            myList.add("test n. " + i);

        assertFalse(listTest.containsAll(myList));
        subList.addAll(3, myList);
        assertTrue(subList.containsAll(myList));
        assertTrue(listTest.containsAll(myList));
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

        // backing
        evenList = new ListAdapter();

        for (int i = 0; i < 100; i++)
            listTest.add(i);

        assertFalse(listTest.retainAll(evenList));

        for (int i = 0; i < 100; i++)
            if (i % 2 == 0)
                evenList.add(i);

        subList = listTest.subList(20, 40);

        assertTrue(subList.removeAll(evenList));

        HIterator iter = subList.iterator();
        while (iter.hasNext())
            assertTrue(((int) iter.next() % 2) != 0);// only odd numbers left
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

        // backing
        init();
        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        subList = listTest.subList(0, 20);

        subList.retainAll(evenList);
        listTest.removeAll(evenList);
        assertTrue(listTest.isEmpty());
    }

    @Test
    public void clearTest() {

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        listTest.clear();
        assertEquals(listTest.size(), 0);

        // backing
        init();
        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        subList = listTest.subList(2, 18);
        subList.clear();
        assertTrue(subList.isEmpty());
        assertEquals(4, listTest.size());
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

        subList = listTest.subList(0, 20);

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

        // backing list -> sublist
        assertTrue(subList.contains("test"));
        assertEquals("test", subList.get(2));

        // backing sublist -> list
        subList.set(2, "nuovo test");
        assertTrue(listTest.contains("nuovo test"));
        assertEquals("nuovo test", listTest.get(2));
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

        // backing
        subList = listTest.subList(0, 1);
        subList.add(0, "test");
        assertEquals("test", listTest.get(0));

        init();
        for (int i = 0; i < 20; i++) {
            listTest.add("valore n. " + i);
        }
        subList = listTest.subList(4, 13);
        subList.add(4, "test");
        assertEquals("test", listTest.get(8));
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

        // backing
        init();
        for (int i = 0; i < 20; i++) {
            listTest.add("valore n. " + i);
        }
        subList = listTest.subList(4, 13);
        assertTrue(listTest.contains("valore n. 8"));
        subList.remove(4);
        assertFalse(listTest.contains("valore n. 8"));
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
    public void firstListIteratorTest() {

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