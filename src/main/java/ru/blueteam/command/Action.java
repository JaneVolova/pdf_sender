package ru.blueteam.command;

import ru.blueteam.dto.NoteDto;
import ru.blueteam.model.Note;
import ru.blueteam.service.LogbookService;

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
        List<Note> listNotes = logbookService.findAllNotesByStudent(request.getParameter("fio"));
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

    void updateNote(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fio = request.getParameter("fio");
        Integer noteId = Integer.parseInt(request.getParameter("noteId"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        String description = request.getParameter("description");

        Note newNote = new Note(noteId, fio, date, description);
        logbookService.updateNote(newNote);

        request.getServletContext().getRequestDispatcher("/?action=showAllNotesByDay").forward(request, response);
    }

    void createForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fio = request.getParameter("fio");
        request.setAttribute("fio", fio);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/create.jsp");
        dispatcher.forward(request, response);
    }

    void createNote(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fio = request.getParameter("fio");
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        String description = request.getParameter("description");

        NoteDto newNote = NoteDto.builder()
                .fio(fio)
                .date(date)
                .description(description)
                .build();
        logbookService.createNote(newNote);

        request.getServletContext().getRequestDispatcher("/?action=showAllNotesByDay").forward(request, response);
    }

    void deleteNote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logbookService.deleteNote(Integer.parseInt(request.getParameter("noteId")));

        request.getServletContext().getRequestDispatcher("/?action=showAllNotesByDay").forward(request, response);
    }

    void showAllStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> listName = logbookService.listName();
        request.setAttribute("listName", listName);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/listUser.jsp");
        dispatcher.forward(request, response);
    }
}











