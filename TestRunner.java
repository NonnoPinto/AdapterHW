/**
 * @author Giovanni Zago - 1226024 - giovanni.zago.3@studenti.unipd.it
 * @description HomeWord, IDS1, a.a. 2020-21
 */

package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import junit.runner.Version;

public class TestRunner {

    public static void main(String argv[]){
        /*System.out.println("\n################Giovanni Zago 1226024 UniPd################\n");
        System.out.println("========Testing class for MapAdapter and ListAdapter========\n");
        System.out.println("JUnit version: " + Version.id());

        System.out.println("\n*****ListAdapter*****");
        Result testResultsL = JUnitCore.runClasses(ListAdapterTest.class);

        System.out.println("Successfull Test: " + testResultsL.wasSuccessful());
        System.out.println("Executed  Test: " + testResultsL.getRunCount());
        System.out.println("**End of ListAdapter Tests**");

        System.out.println("\n*****MapAdpter*****");
        Result testResultsM = JUnitCore.runClasses(MapAdapterTest.class);

        System.out.println("Successfull Test: " + testResultsM.wasSuccessful());
        System.out.println("Executed Test: " + testResultsM.getRunCount());
        System.out.println("***End of MapAdapter Tests***");

        int total = testResultsL.getRunCount() + testResultsM.getRunCount();
        int fail = testResultsL.getFailures().size() + testResultsM.getFailures().size();
        System.out.println("\n" + total + " test has been made and " + fail + " are failed.");*/
        JUnitCore.main("myTest.MapAdapterTest", "myTest.ListAdapterTest");
    }

}
