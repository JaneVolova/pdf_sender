package ru.blueteam.command;

import ru.blueteam.service.LogbookService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAllStudents implements Command {
    private LogbookService logbookService;

    public ShowAllStudents(LogbookService logbookService) {
        this.logbookService = logbookService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new Action(this.logbookService).showAllStudents(request, response);
    }
}
