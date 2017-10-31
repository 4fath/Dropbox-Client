package org.fath.app;

public enum CommandEnum {

    AUTH("auth"),
    INFO("info"),
    LIST("list");

    private String value;

    CommandEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CommandEnum getValidCommand(String arg){
        for (CommandEnum type : CommandEnum.values()){
            if (type.getValue().equals(arg)){
                return type;
            }
        }
        return null;
    }
}
