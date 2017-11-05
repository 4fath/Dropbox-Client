package org.fath;

import com.dropbox.core.DbxException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.ExpectedException;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple App.
 */
public class AppTest  {

    private static final String EOL = System.getProperty("line.separator");

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();


    @Before
    public void setUp() {

    }

    @Test
    public void test_not_enter_a_command() throws Exception{

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(bytes));
            App.main(new String[]{});
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("No command found.%s",EOL), bytes.toString());
    }

    @Test
    public void test_enter_less_than_two_command() throws Exception{

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(bytes));
            App.main(new String[]{"justOneCommand"});
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("No command found.%s",EOL), bytes.toString());
    }



    @Test
    public void test_enter_not_valid_and_min_two_command() throws Exception{

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(bytes));
            App.main(new String[]{"firstCommand","secondCommand"});
        } finally {
            System.setOut(console);
        }
        assertEquals(String.format("Unsupported command : %s.%s","firstCommand",EOL), bytes.toString());
    }



    @Test
    public void test_enter_auth_command_and_less_than_two_parameters() throws Exception{

        exit.expectSystemExit();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(bytes));
            App.main(new String[]{"auth","secondCommand"});
        } finally {
            System.setOut(console);
        }
        // assertThat(bytes.toString(), containsString("Invelid usage of 'auth' command."));
    }


}
