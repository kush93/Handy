package tests;
import junit.framework.*;

import businesslayer.TextNoteBL;
import persistancelayer.TextNotePL;

import static junit.framework.Assert.assertTrue;

/**
 * Created by kushal on 2016-02-21.
 */
import android.test.suitebuilder.TestSuiteBuilder;

public class TestAll extends TestSuite
{
    public static Test suite()
    {
        return  new TestSuiteBuilder(TestAll.class).includeAllPackagesUnderHere().build();
    }
    public TestAll()
    {
        super();
    }
}
