package tests.Unit_tests;
import junit.framework.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by kushal on 2016-02-21.
 */

//public class TestAll extends TestSuite
//{
  //  public static Test suite()
    //{
      //  return  new TestSuiteBuilder(TestAll.class).includeAllPackagesUnderHere().build();
    //}
    //public TestAll()
    //{
      //  super();
    //}
//}
public class TestAll
{
    public static Test suite()
    {
        TestSuite suite= new TestSuite();
        suite.addTestSuite(TestList.class);
        return suite;
    }
}