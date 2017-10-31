package org.fath.factory;

import org.fath.app.CommandEnum;
import org.fath.domain.AuthCommand;
import org.fath.domain.Command;
import org.fath.domain.InfoCommand;
import org.fath.domain.ListCommand;

public class CommandFactory {

    public static Command getCommand(String[] args){
        Command command = null;
        String firstArg = args[0];
        CommandEnum commandType = CommandEnum.getValidCommand(firstArg);
        if (commandType != null){
            if (CommandEnum.AUTH.equals(commandType)){
                 command = new AuthCommand(args);
            }else if (CommandEnum.INFO.equals(commandType))  {
                command = new InfoCommand(args);
            }else if (CommandEnum.LIST.equals(commandType)){
                command = new ListCommand(args);
            }
        }
        return command;
    }
}
