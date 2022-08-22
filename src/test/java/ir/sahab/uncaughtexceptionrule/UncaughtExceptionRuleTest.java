package ir.sahab.uncaughtexceptionrule;

import org.apache.log4j.BasicConfigurator;
import org.junit.*;

public class UncaughtExceptionRuleTest {

    @Rule
    public UncaughtExceptionRule rule = new UncaughtExceptionRule();

    @BeforeClass
    public static void setup() {
        BasicConfigurator.configure();
    }

    @Test
    public void testAssertOnUnhandledException() throws InterruptedException {
        Thread t = new Thread(() -> {
            throw new ArithmeticException();
        });
        t.start();
        t.join();

        Assert.assertNotNull(rule.getException());
        Assert.assertTrue(rule.getException() instanceof ArithmeticException);
        // Clear the exception for passing the running unit test
        rule.clearException();
    }

    // It is a test to test failure of test. So it is ignored.
    // If you are in doubt, you can delete the @Ignore annotation!
    @Ignore
    @Test
    public void testFailureForUnhandledException() throws InterruptedException {
        Thread t = new Thread(() -> {
            throw new ArithmeticException();
        });
        t.start();
        t.join();
    }

    // In case of exception in test thread and another thread we should show all exceptions
    @Ignore
    @Test
    public void testFail() throws RuntimeException, InterruptedException {
        Thread t = new Thread(() -> {
            throw new IllegalStateException("Exception occurred in another thread");
        });
        t.start();
        t.join();
        throw new IllegalStateException("Exception occurred in unit test's thread");
    }

}
