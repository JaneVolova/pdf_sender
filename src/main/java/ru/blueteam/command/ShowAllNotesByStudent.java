package ru.blueteam.command;

import ru.blueteam.controller.Controller;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllNotesByStudent implements Command {
    private LogbookService logbookService;

    @Override
    public Dashboard execute(HttpServletRequest request) {
        Dashboard resultPage = null;
        String studentName = request.getParameter("fio");
        Controller con = new Controller();
        LogbookRepositoryImpl logbookRepository = new LogbookRepositoryImpl(con.getDataSource());
        this.logbookService = new LogbookServiceImpl(logbookRepository);
        List<Note> listNotes = logbookService.findAllNotesByStudent(studentName);

        request.setAttribute("listNotes", listNotes);
        resultPage = new Dashboard("/jsp/studentsNote.jsp");

        return resultPage;
    }

}
