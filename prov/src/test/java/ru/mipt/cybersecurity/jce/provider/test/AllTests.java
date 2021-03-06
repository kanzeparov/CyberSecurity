package ru.mipt.cybersecurity.jce.provider.test;

import java.security.Security;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ru.mipt.cybersecurity.jce.provider.BouncyCastleProvider;
import ru.mipt.cybersecurity.util.test.SimpleTestResult;

public class AllTests
    extends TestCase
{
    public static void main (String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite("JCE Tests");

        suite.addTestSuite(SimpleTestTest.class);
        
        return new BCTestSetup(suite);
    }

    public static class SimpleTestTest
       extends TestCase
    {
        public void testJCE()
        {
            ru.mipt.cybersecurity.util.test.Test[] tests = RegressionTest.tests;

            for (int i = 0; i != tests.length; i++)
            {
                SimpleTestResult  result = (SimpleTestResult)tests[i].perform();

                if (!result.isSuccessful())
                {
                    if (result.getException() != null)
                    {
                        result.getException().printStackTrace();
                    }
                    fail(result.toString());
                }
            }
        }
    }

    static class BCTestSetup
        extends TestSetup
    {
        public BCTestSetup(Test test)
        {
            super(test);
        }

        protected void setUp()
        {
            Security.addProvider(new BouncyCastleProvider());
        }

        protected void tearDown()
        {
            Security.removeProvider("BC");
        }
    }

}
