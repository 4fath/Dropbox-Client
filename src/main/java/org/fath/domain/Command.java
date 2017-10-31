package org.fath.domain;

import java.io.IOException;

public interface Command {

    void execute() throws IOException;

    void validateParameters();
}
