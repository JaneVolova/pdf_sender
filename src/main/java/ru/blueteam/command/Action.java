package ru.blueteam.command;

import ru.blueteam.dto.NoteDto;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Action {

    private LogbookService logbookService;

    public Action(LogbookService logbookService) {
        this.logbookService = logbookService;
    }

    public void showAllNotesByDay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Note> listNotesByDay = logbookService.findAllNotesByDay();
        request.setAttribute("listNotesByDay", listNotesByDay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/listNotes.jsp");
        dispatcher.forward(request, response);
    }

    void showAllNotesByStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Note> listNotes = logbookService.findAllNotesByStudent(Integer.parseInt(request.getParameter("studentId")));
        request.setAttribute("listNotes", listNotes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/studentsNote.jsp");
        dispatcher.forward(request, response);
    }

    void showNoteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Note note = logbookService.findById(Integer.parseInt(request.getParameter("noteId")));
        request.setAttribute("note", note);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/update.jsp");
        dispatcher.forward(request, response);
    }

    void updateNote(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Integer noteId = Integer.parseInt(request.getParameter("noteId"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        String description = request.getParameter("description");

        Note newNote = new Note(noteId, studentId, date, description);
        logbookService.updateNote(newNote);

        response.sendRedirect("/");
    }

    void createNote(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
//        LocalDate date = LocalDate.parse(request.getParameter("date"));
        String description = request.getParameter("description");

        NoteDto newNote = NoteDto.builder()
                .studentId(studentId)
                .date(LocalDate.now())
                .description(description)
                .build();
        logbookService.createNote(newNote);

        response.sendRedirect("/");
    }

    void deleteNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logbookService.deleteNote(Integer.parseInt(request.getParameter("noteId")));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/listNotes.jsp");
        dispatcher.forward(request, response);
    }

}
