package myTest;

import myAdapter.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class MapAdapterTest {

    MapAdapter mapTest;

    @Before
    public void init(){
        mapTest = new MapAdapter();
    }

    @Test
    public void sizeTest(){
        assertEquals(mapTest.size(), 0);
    }

    @Test
    public void emptyTest(){
        assertTrue(mapTest.isEmpty()==true);
    }
}
