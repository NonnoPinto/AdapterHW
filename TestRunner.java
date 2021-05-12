package myTest;

import org.junit.runner.JUnitCore;

public class TestRunner {

    public static void main(String argv[]){
        JUnitCore.main("myTest.MapAdapterTest");
        JUnitCore.main("myTest.ListAdapterTest");
    }
  
}
