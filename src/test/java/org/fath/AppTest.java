package org.fath;

import com.dropbox.core.DbxException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {


    private static String APP_KEY = "i353qnxe0gx5odm";
    private static String APP_SECRET = "b1brlhfpm1n7gji";

    private static String AUTH_COMMAND = "auth";
    private static String INFO_COMMAND = "info";
    private static String LIST_COMMAND = "list";

    public void tp() throws IOException, DbxException {
        App.main(new String[]{"fsdf", "sdfs"});
        assertTrue(true);
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testAuth() throws IOException, DbxException {
        App.main(new String[]{AUTH_COMMAND, APP_KEY, APP_SECRET});
        assertTrue(true);
    }


    public void testInfo() throws IOException, DbxException {
        //App.main(new String[]{INFO_COMMAND, "sdfs"});
        String fileName = "loa.pdf";

        int indexof = fileName.lastIndexOf(".");
        String mimiType = fileName.substring(indexof, fileName.length());
    }

    public void testLıst() throws IOException, DbxException {



        long value =  123456789;
        float val = (float) value / (1024*1024);
        String formattedValue = String.format("%.2f", val);

        assertTrue(true);
    }
}
