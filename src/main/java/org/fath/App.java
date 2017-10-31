package org.fath;

import com.dropbox.core.DbxException;
import org.fath.domain.Command;
import org.fath.factory.CommandFactory;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException, DbxException {

        Command command = CommandFactory.getCommand(args);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("unsupported command : " + args[0]);
            System.exit(1);
        }

    }
}
