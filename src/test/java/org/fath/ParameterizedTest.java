package org.fath;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;


@RunWith(Parameterized.class)
public class ParameterizedTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    private String command;

    // Inject via constructor
    public ParameterizedTest(String command) {
        this.command = command;
    }

    @Parameterized.Parameters(name = "{index}: testCommand - {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"auth"},
                {"info"},
                {"list"}
        });
    }

    @Test
    public void test_just_one_valid_command() throws Exception{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            System.setOut(new PrintStream(bytes));
            App.main(new String[]{command});
        } finally {
            System.setOut(console);
        }
        assertThat(bytes.toString(), containsString("No command found."));
    }
}
