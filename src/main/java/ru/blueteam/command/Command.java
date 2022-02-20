package ru.blueteam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public interface Command {

    Dashboard execute(HttpServletRequest request) throws SQLException;

}
