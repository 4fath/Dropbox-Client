package org.fath;

import com.dropbox.core.DbxException;
import org.fath.domain.Command;
import org.fath.factory.CommandFactory;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException, DbxException {

        if (args != null && args.length >= 2){
            Command command = CommandFactory.getCommand(args);
            if (command != null) {
                command.execute();
            } else if(args[0] != null) {
                System.out.println(String.format("Unsupported command : %s.", args[0]));
            }else{
                System.out.println("No command found.");
            }
        }else {
            System.out.println("No command found.");
        }


    }
}
