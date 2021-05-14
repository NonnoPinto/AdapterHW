/**
 * @author Giovanni Zago - 1226024 - giovanni.zago.3@studenti.unipd.it
 * @description HomeWord, IDS1, a.a. 2020-21
 */

package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import junit.runner.Version;

public class TestRunner {

    public static void main(String argv[]){
        /*System.out.println("\n################Giovanni Zago 1226024 UniPd################\n");
        System.out.println("========Testing class for MapAdapter and ListAdapter========\n");
        System.out.println("JUnit version: " + Version.id());
        
        //ListAdapter
        System.out.println("\n========ListAdapter=========");
        Result testResultsL = JUnitCore.runClasses(ListAdapterTest.class);
        
        for (Failure fail : testResultsL.getFailures())
            System.out.println("Failed Tests: " + fail.toString());

        System.out.println("Successfull Test: " + testResultsL.wasSuccessful());
        System.out.println("Executed  Test: " + testResultsL.getRunCount());
        System.out.println("==End of ListAdapter Tests==");

        //MapAdapter
        System.out.println("\n==========MapAdapter==========");
        Result testResultsM = JUnitCore.runClasses(MapAdapterTest.class);
        
        for (Failure fail : testResultsM.getFailures())
            System.out.println("Failed Test: " + fail.toString());

        System.out.println("Successfull Test: " + testResultsM.wasSuccessful());
        System.out.println("Executed Test: " + testResultsM.getRunCount());
        System.out.println("===End of MapAdapter Tests===");

        int total = testResultsL.getRunCount() + testResultsM.getRunCount();
        int fail = testResultsL.getFailures().size() + testResultsM.getFailures().size();
        System.out.println("\n" + total + " test has been made and " + fail + " are failed.");*/
        JUnitCore.main("myTest.MapAdapterTest", "myTest.ListAdapterTest");
    }
  
}
