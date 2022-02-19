package ru.blueteam.command;

import javax.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    public Dashboard execute(HttpServletRequest request) {
        return new Dashboard("/jsp/user-list.jsp", false);

    }
}
