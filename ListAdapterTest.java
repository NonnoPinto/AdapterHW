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
     * <b>Method</b> contains() <br>
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

    /**
     * <b>Method</b> : toArray()</b>
     *
     * <b>Summary</b> : Create an array whit the Array Object constructor,
     * initialize our list, add the elements of the list to the array previously
     * created and then compare the list and the array.</br>
     *
     * <b>Design</b> : Check if toArray mathod makes the same array as "manually"
     * made.</br>
     *
     * <b>Test description</b> : Array created whit the method and check if elements
     * of array are equals to the elements in the list.</br>
     *
     * <b>Pre-conditions</b> : List initialized</br>
     *
     * <b>Post-condition</b> : Arrays has to be the same</br>
     *
     * <b>Exptected results</b> : Two equals arrays</br>
     */

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

    /**
     * <b>Method</b> : add()</br>
     *
     * <b>Summary</b> : Add a new elements checking if it is find inside list, The
     * same for sublist, checking backing function sublist -> list</br>
     *
     * <b>Design</b> : Right memory allocate.</br>
     *
     * <b>Test description</b> : Add add elements and checking if its inside. Same
     * with sublist, adding frrom it (backing)</br>
     *
     * <b>Pre-conditions</b> : List initialized</br>
     *
     * <b>Post-condition</b> : Same elements has to be inside list ad sublist.</br>
     *
     * <b>Exptected results</b> : return an ordered array, throwing
     * NullPointerException if the added element is null.</br>
     */

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

    /**
     * <b>Method</b> : remove(Object o)</br>
     *
     * <b>Summary</b> : Try to remove null element and thorw excpetion. Then list is
     * filled with elements and they are removed and serached. False its exptected.
     * Same process is made from sublit, to test backing</br>
     *
     * <b>Design</b> : Check if remove method remove the object from the list.</br>
     *
     * <b>Test description</b> : Adding and removing elements from list and
     * sublist.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized whit 0 size, object must be
     * not null.</br>
     *
     * <b>Post-condition</b> : Removed elements not in list and subklist</br>
     *
     * <b>Exptected results</b> : The object must be removed if exist otherwise do
     * nothing, from lisdt end sublists.</br>
     */
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

    /**
     * <b>Method</b> : containsAll(HCollection c)</b>
     *
     * <b>Summary</b> : Initialize the list and create and initialize another with
     * half elements of the global list, then check if one of these contains (or
     * doesnt) the other</br>
     *
     * <b>Design</b> : Check that the list contains (or not) a collection.</br>
     *
     * <b>Test description</b> : Check exception, then initialize it, create and
     * initialize another list and check if one contains other. Lastly, fill second
     * list with different elements and check false </br>
     *
     * <b>Pre-conditions</b> : List initialized, collection with valid object.</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : Return true if the list contains the collection,
     * false otherwise, throwing NullPointerException if the collection is </br>
     */
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

    /**
     * <b>Method</b> : addAll(HCollection c)</b>
     *
     * <b>Summary</b> : Create and add a collection to the list (firstly empty, then
     * with some elements). The list must be have the same size of the
     * collection</br>
     *
     * <b>Design</b> : Check the insertion of element in a list, list size must
     * increase.</br>
     *
     * <b>Test description</b> : Check exception, the global list should contains
     * null, then initialize it, create and initialize another list and check if one
     * contains other.</br>
     *
     * <b>Pre-conditions</b> : List initialized, collection initialized.</br>
     *
     * <b>Post-condition</b> : List and collection has to be equals.</br>
     *
     * <b>Exptected results</b> : The method return true if the list changes size,
     * false otherwise throwing NullPointer exception if the collection is null</br>
     */
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

    /**
     * <b>Method</b> : addAll(int index, HCollection c)</br>
     *
     * <b>Summary</b> : Add a collection to the list in a specifical index, then
     * check get if from that index i found the same collection. Same with sublist,
     * to check backing</br>
     *
     * <b>Design</b> : The collection must be insert in the right place on the list
     * and the list size must be increase.</br>
     *
     * <b>Test description</b> : Check the NullPointerException, insert an element
     * at the index and check. Insert from sublist and check</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized, with zero dimension.
     * Collection initialized. Int index</br>
     *
     * <b>Post-condition</b> : List has to contain collection, alse when inserted
     * from sublist.</br>
     *
     * <b>Exptected results</b> : The list will contain the elements of the
     * collection starting from the given index, throwing IndexOutOfBound exception
     * if the index is < 0 or > size() and NullPointerException if the collection is
     * null.</br>
     */

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

    /**
     * <b>Method</b> : removeAll(HCollection c)</br>
     *
     * <b>Summary</b> : Filling two lists with different elements, then check
     * removeAll return false. Insert a common element and reove it. Then remove
     * even numbers and check odd numbers still in list. Same with sublist</br>
     *
     * <b>Design</b> : Remove all the collection elements from a list.</br>
     *
     * <b>Test description</b> : Add common and non common elements andr trying to
     * remove them, returning false and true. Same tests for list and sublist,
     * checking for backing</br>
     *
     * <b>Pre-conditions</b> : List initialized, collection initialized.</br>
     *
     * <b>Post-condition</b> : List without collection elements.</br>
     *
     * <b>Exptected results</b> : The method return true if the list changes size,
     * false otherwise, throws NullPointer exception if the collection is null.</br>
     */
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

    /**
     * <b>Method</b> : retainAll(HCollection c)</br>
     *
     * <b>Summary</b> : Add 29 elements, retain even elements from the list and
     * check if only even are still in the list. Same with sublist</br>
     *
     * <b>Design</b> : Remove all collection elements from a list os a sublist (with
     * backing).</br>
     *
     * <b>Test description</b> : Check the retain of elements after adding elementa
     * from the collection.</br>
     *
     * <b>Pre-conditions</b> : List initialized, collection initialized.</br>
     *
     * <b>Post-condition</b> : List with only collection elements.</br>
     *
     * <b>Exptected results</b> : The method return true if the list changes size,
     * false otherwise, throwing NullPointer exception if the collection is
     * null.</br>
     */
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

    /**
     * <b>Method</b> : clear()</br>
     *
     * <b>Summary</b> : Delete all the elements from a non empty list and check the
     * list is void. Same from sublist, with backing.</br>
     *
     * <b>Design</b> : Check deleting all elements from the list.</br>
     *
     * <b>Test description</b> : Check if method actually clear the list.</br>
     *
     * <b>Pre-conditions</b> : List initialized.</br>
     *
     * <b>Post-condition</b> : Empty list.</br>
     *
     * <b>Exptected results</b> : Empty list.</br>
     */

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

    /**
     * <b>Method</b> : equals(Object o)</br>
     *
     * <b>Summary</b> : Add same elements into two lists and different in a third
     * one. Then check the list elements is the same of same lsit and different from
     * the third one.</br>
     *
     * <b>Design</b> : Check the equality of objects.</br>
     *
     * <b>Test description</b> : Add elements to the list and check if it's equal as
     * the collection.</br>
     *
     * <b>Pre-conditions</b> : List initialized, collection with valid objects
     * .</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : Equals return true if has the same elements as
     * collection.</br>
     */
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

    /**
     * <b>Method</b> : get(int index)</br>
     *
     * <b>Summary</b> : It adds to the list few elements, call the get method on a
     * intex and check if the element in that index (that we now) it is indeed
     * that</br>
     *
     * <b>Design</b> : Want to compare equals elements in a specifical index,
     * knowing the elements in the list.</br>
     *
     * <b>Test description</b> : Test the method get comparing equals elements.</br>
     *
     * <b>Pre-conditions</b> : List initialized, valid index.</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : The method returns the elements in a specifical
     * index throwing IndexOutOfBoung exception if the index goes out of buond.</br>
     */
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

    /**
     * <b>Method</b> : set(int index, Object elements)</br>
     *
     * <b>Summary</b> : It set an element to the list, replacing an old element in a
     * specifical index for couple times and checks if the list contains these
     * elements. Backing is checked list->sublist and viceversa</br>
     *
     * <b>Design</b> : Test set the method with different objects and indexes.</br>
     *
     * <b>Test description</b> : Set couple elements checking if the list contains
     * these elements.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialize, a valid index and a non-null
     * object.</br>
     *
     * <b>Post-condition</b> : List need to have last setted element.</br>
     *
     * <b>Exptected results</b> : Set returns the object that it replaces, list size
     * will not change, throwing NullPointerException if the given object is
     * null.</br>
     */
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

    /**
     * <b>Method</b> : add(int index, Object elements)</br>
     *
     * <b>Summary</b> : Add elements to the list, checking the size, add an element
     * in some positions, checking if the elements are in the right position. Same
     * for backing from sublist</br>
     *
     * <b>Design</b> : Test the right insert of elements in the right position.</br>
     *
     * <b>Test description</b> : Insert an element right, lefting all next
     * elements.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized, object initialized and a
     * valid index.</br>
     *
     * <b>Post-condition</b> : List can come back void.</br>
     *
     * <b>Exptected results</b> : List will contains two new elements, increasing of
     * three it size, throwing NullPointerException if the given object is null,
     * IndexOutOfBoundExcpetion for wrong index.</br>
     */

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

    /**
     * <b>Method</b> : remove(int index)</br>
     *
     * <b>Summary</b> : It removes some elements from a list filled with a
     * collection, checking if the list actually not contains the elements, the list
     * size must be decreased. Same for backing from sublist</br>
     *
     * <b>Design</b> : Test the remove method with an index to remove elements from
     * a list.</br>
     *
     * <b>Test description</b> : The list is initially filled, than it removes some
     * elements and check the size.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized, valid index.</br>
     *
     * <b>Post-condition</b> : List must not have removed elements.</br>
     *
     * <b>Exptected results</b> : The method removes an object from the list in a
     * specifical index, returning the deleted object, throwing IndexOutOfBound
     * exception if the index is lower or higher the list first and last
     * element.</br>
     */
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

    /**
     * <b>Method</b> : indexOf(Object o)</br>
     *
     * <b>Summary</b> : It adds a collection of element into the list, knowing the
     * arrangement, check if the method return the element we know in the index we
     * know. Lastly we test the method with an element not on the list and it must
     * be return -1.</br>
     *
     * <b>Design</b> : Gettin the right index of an element, -1 if its not in the
     * list.</br>
     *
     * <b>Test description</b> : Insert elements into the list and check if they
     * have rhe right index.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized, object initialized with a
     * valid value.</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : The method will return the index, of the given
     * object returning -1 if the element is not on it, throwing
     * NullPointerException if it the object is null.</br>
     */
    @Test
    public void indexOfTest() {
        assertThrows(NullPointerException.class, () -> {
            listTest.indexOf(null);
        });

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        assertEquals(2, listTest.indexOf("valore n. 2"));
        assertEquals(-1, listTest.indexOf("test"));
    }

    /**
     * <b>Method</b> : lastIndexOf(Object o)</br>
     *
     * <b>Summary</b> : It adds to the list a collection of same elemetns and check
     * if the last time the object appears is in the right position.</br>
     *
     * <b>Design</b> : Returns -1 if it not on it, the index of the last appear
     * otherwise.</br>
     *
     * <b>Test description</b> : Add a collections to the list and check the last
     * index of an element.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized, object initialized with a
     * valid value.</br>
     *
     * <b>Post-condition</b> : N/A</br>
     *
     * <b>Exptected results</b> : The method will return the last index, of the
     * given object into the list, returning -1 if the element is not on it,
     * throwing NullPointerException if it the object is null.</br>
     */
    @Test
    public void lastIndexOfTest() {

        assertThrows(NullPointerException.class, () -> {
            listTest.lastIndexOf(null);
        });

        for (int i = 0; i < 20; i++)
            listTest.add("test");

        assertEquals(19, listTest.lastIndexOf("test"));
        assertEquals(-1, listTest.lastIndexOf("non test"));
    }

    /**
     * <b>Method</b> : listIterator()</br>
     *
     * <b>Summary</b> : It calls the iterator on the void list, it has no elements
     * to iterate, it adds elements to the list an it iterates all the elements, the
     * iterated object will be the same as the number of elements on the list.</br>
     *
     * <b>Design</b> : Use a iterator for a list that run forward.</br>
     *
     * <b>Test description</b> : Iterate a void list and then a fill it.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized.</br>
     *
     * <b>Post-condition</b> : An iterator over the list.</br>
     *
     * <b>Exptected results</b> : the iterator runs over the list.</br>
     */
    @Test
    public void firstListIteratorTest() {

        HListIterator iter = listTest.listIterator();
        assertFalse(iter.hasNext());

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        int i = 0;
        while (iter.hasNext()) {
            assertEquals("valore n. " + i, iter.next());
            i++;
        }

        assertFalse(iter.hasNext());

        assertEquals(listTest.size(), 20);
    }

    /**
     * <b>Method</b> : listIterator(int index)</br>
     *
     * <b>Summary</b> : Add a collection to the list, call an iterator on a
     * specifical position and verify if it has next element and if the next element
     * of iterator is right.</br>
     *
     * <b>Design</b> : Right runnin through of element by a list iterator.</br>
     *
     * <b>Test description</b> : Create and test the method hasNext and next of a
     * list iterator.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized, valid index.</br>
     *
     * <b>Post-condition</b> : A list iterator starting from given index.</br>
     *
     * <b>Exptected results</b> : next() will iterates as as many elements as those
     * contained into the list (exepteds index), iteration starts from index,
     * throwing IndexOutOfBound exception if index is not valid.</br>
     */
    @Test
    public void secondListIterator() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            listTest.listIterator(2);
        });

        for (int i = 0; i < 20; i++)
            listTest.add("valore n. " + i);

        HListIterator iterator = listTest.listIterator(2);

        assertTrue(iterator.hasNext());
        assertEquals("valore n. 2", iterator.next());
    }

    /**
     * <b>Method</b> : subList(int fromIndex, int toIndex)</br>
     *
     * <b>Summary</b> : Thorw excpetion with different index errros. Then make 4
     * sublist starting from a list and each from previous one. Last, check if
     * sub(n) contains sub(n+1)</br>
     *
     * <b>Design</b> : Right creation of a sublist, with index from an to, starting
     * by a not-empty list.</br>
     *
     * <b>Test description</b> : Create some sublists, testing their size and
     * elements</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized.</br>
     *
     * <b>Post-condition</b> : Some subslit backed from list.</br>
     *
     * <b>Exptected results</b> : The method will return a sublist that is a portion
     * of the list.Sublist changes will reflect in the list, throwing
     * IndexOutOfBound exception if the indexes are not valid.</br>
     */
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

    /**
     * <b>Method</b> hasNext()</br>
     *
     * <b>Summary</b> : it checks if the hasNext() method from works as intended and
     * returns the expected boolean. Is it tested at the beginning and at the end of
     * the list</br>
     *
     * <b>Design</b> : Test if hasNext sees the end of the iterator</br>
     *
     * <b>Test Description</b> : hasNext() tested in different points of the
     * list</br>
     *
     * <b>Pre-conditions</b> : A list and an iterator</br>
     *
     * <b>Post-condition</b> : An iterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : true if its not at the end of the lsit, false
     * otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> next()</br>
     *
     * <b>Summary</b> : it checks if the next() method from an iterator returns the
     * next element of the list, or excpetion if its already on the last one</br>
     *
     * <b>Design</b> : next is called on empty and filled list, verifying the
     * returned object is the expected one</br>
     *
     * <b>Test Description</b> : Test if next returns right element or excpetin,
     * whem its at the end of the list</br>
     *
     * <b>Pre-conditions</b> : List and iterator</br>
     *
     * <b>Post-condition</b> : An iterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : Next element if it exist, excpetion otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> remove()</br>
     *
     * <b>Summary</b> : it checks if the remove method from a list works as intended
     * and remove objet from it. It tried to remove pbject from empty list, then
     * from a filled list. Check if size is decreades and righ element removed</br>
     *
     * <b>Design</b> : Tries to remove element from an empty and an filled list,
     * checking size and removed elements</br>
     *
     * <b>Test Description</b> : Tries to remove element from an empty and an filled
     * list, checking size and removed elements</br>
     *
     * <b>Pre-conditions</b> : List and iteratore</br>
     *
     * <b>Post-condition</b> : List without removed elements</br>
     *
     * <b>Expected results</b> : throwing right exception when called, returning
     * removed elements, decrese list size</br>
     *
     */
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

        while (iterTest.hasNext())
            iterTest.next();

        assertEquals(listTest.size(), 99);
        assertFalse(listTest.contains("valore n. 0"));
    }

    /**
     * <b>Method</b> add()</br>
     *
     * <b>Summary</b> : it checks if the added element by the iterator method is
     * contained in the list</br>
     *
     * <b>Design</b> : Add en element and check if its inside, in the right
     * position</br>
     *
     * <b>Test Description</b> : Add element, check the position of it</br>
     *
     * <b>Pre-conditions</b> : List and iterator</br>
     *
     * <b>Post-condition</b> : List with added element</br>
     *
     * <b>Expected results</b> : List with added element</br>
     *
     */
    @Test
    public void listAddTest() {
        craeateIter();
        listIter.add("test");
        assertTrue(listIter.hasNext());
        assertEquals("test", listIter.next());
    }

    /**
     * <b>Method</b> hasNext()</br>
     *
     * <b>Summary</b> : it checks if the hasNext() method from listIterator works as
     * intended and returns the expected boolean. Is it tested at the beginning and
     * at the end of the list</br>
     *
     * <b>Design</b> : Test if hasNext sees the end of the listIterator</br>
     *
     * <b>Test Description</b> : hasNext() tested in different points of the
     * list</br>
     *
     * <b>Pre-conditions</b> : A list and an ListIterator</br>
     *
     * <b>Post-condition</b> : A listIterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : true if its not at the end of the lsit, false
     * otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> hasPrevious()</br>
     *
     * <b>Summary</b> : it checks if the hasPrevious() method from a listItreator
     * works as intended and returns the expected boolean. Is it tested at the
     * beginning and at the end of the list</br>
     *
     * <b>Design</b> : Test if hasPrevious sees the beginning of the
     * ListIterator</br>
     *
     * <b>Test Description</b> : hasPrevious() tested in different points of the
     * list</br>
     *
     * <b>Pre-conditions</b> : A list and an ListIterator</br>
     *
     * <b>Post-condition</b> : A ListIterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : false if its at the beginning of the lsit, true
     * otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> hasNext()</br>
     *
     * <b>Summary</b> : it checks if the hasNext() method from a listIterator works
     * as intended and returns the expected boolean. Is it tested at the beginning
     * and at the end of the list</br>
     *
     * <b>Design</b> : Test if hasNext sees the end of the listIterator</br>
     *
     * <b>Test Description</b> : hasNext() tested in different points of the
     * list</br>
     *
     * <b>Pre-conditions</b> : A list and an listIterator</br>
     *
     * <b>Post-condition</b> : A listIterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : true if its not at the end of the lsit, false
     * otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> nextIndex()</br>
     *
     * <b>Summary</b> : it checks if the nextIndex() method from listIterator works
     * as intended and returns the expected index. Is it tested at the beginning and
     * at the end of the list</br>
     *
     * <b>Design</b> : Test if NextIndex returns roght index</br>
     *
     * <b>Test Description</b> : NextIndex() tested in different points of the
     * list</br>
     *
     * <b>Pre-conditions</b> : A list and an iterator</br>
     *
     * <b>Post-condition</b> : An iterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : nextIndex if its not at the end of the list, the
     * last index otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> previous()</br>
     *
     * <b>Summary</b> : it checks if the previous() method from a listIterator works
     * as intended and returns the expected object. Is it tested at the beginning
     * (exception) and at the end of the list. It return the element where the index
     * is and decremenet the element. Calling next and previous one after the other,
     * return always the same element</br>
     *
     * <b>Design</b> : Test if previous() return the right element, or an error if
     * its at the very beginning of the list</br>
     *
     * <b>Test Description</b> : previous() tested in different points of the
     * list</br>
     *
     * <b>Pre-conditions</b> : A list and an listIterator</br>
     *
     * <b>Post-condition</b> : A listIterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : previous element (@index element), excpetion if its
     * at the very begininng of the lsit</br>
     *
     */
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

    /**
     * <b>Method</b> preiovusIndex()</br>
     *
     * <b>Summary</b> : it checks if the previousIndex() method from listIterator
     * works as intended and returns the expected index. Is it tested at the
     * beginning and at the end of the list</br>
     *
     * <b>Design</b> : Test if previousIndex returns right index</br>
     *
     * <b>Test Description</b> : NextIndex() tested in different points of the
     * list</br>
     *
     * <b>Pre-conditions</b> : A list and an iterator</br>
     *
     * <b>Post-condition</b> : An iterator runing thorgh the list</br>
     *
     * <b>Expected results</b> : previous if its not at the beginning of the list,
     * -1 otherwise</br>
     *
     */
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

    /**
     * <b>Method</b> remove()</br>
     *
     * <b>Summary</b> : it checks if the remove method from a listIterator works as
     * intended and remove objet from it. It tried to remove pbject from empty list,
     * then from a filled list</br>
     *
     * <b>Design</b> : Tries to remove element from an empty and an filled list,
     * checking size and removed elements</br>
     *
     * <b>Test Description</b> : Tries to remove element from an empty and an filled
     * list, checking size and removed elements</br>
     *
     * <b>Pre-conditions</b> : List and iteratore</br>
     *
     * <b>Post-condition</b> : List without removed elements</br>
     *
     * <b>Expected results</b> : returning removed elements, decrese list size</br>
     *
     */
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

    /**
     * <b>Method</b> : set</br>
     *
     * <b>Summary</b> : It set an element to the list, at index position, replacing
     * an old element in a specifical index for couple times and checks if the list
     * contains these elements</br>
     *
     * <b>Design</b> : Test set the method with different objects and indexes.</br>
     *
     * <b>Test description</b> : Set couple elements checking if the list contains
     * these elements.</br>
     *
     * <b>Pre-conditions</b> : ListAdapter initialized.</br>
     *
     * <b>Post-condition</b> : List need to have last setted element.</br>
     *
     * <b>Exptected results</b> : list size will not change, index element
     * changed.</br>
     */
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