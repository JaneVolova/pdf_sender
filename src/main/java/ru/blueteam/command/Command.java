package ru.blueteam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    Dashboard execute(HttpServletRequest request);

}
