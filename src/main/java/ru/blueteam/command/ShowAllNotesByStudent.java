package ru.blueteam.command;

import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.service.LogbookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllNotesByStudent implements Command {
    private LogbookService  logbookService;

    @Override
    public Dashboard execute(HttpServletRequest request) {
        Dashboard resultPage = null;
        String pageParameter = request.getParameter("action");
        String studentName = request.getParameter("studentId");


/*
         LogbookRepositoryImpl logbookService = new LogbookRepositoryImpl();
        List<Note> listNotes = logbookService.findAllNotesByDay();
          request.setAttribute("listNotes", listNotes);*/

        resultPage = new Dashboard("/jsp/studentsNote.jsp", false);

        return resultPage;
    }

}
