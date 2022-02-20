package ru.blueteam.repository;

import ru.blueteam.dto.NoteDto;
import ru.blueteam.model.Note;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface LogbookRepository {
    List<Note> findAllNotesByDay(LocalDate date) throws SQLException;

    List<Note> findAllNotesByStudent(String fio);

    Note findNoteById(Integer noteId);

    void updateNote(Note note);

    void deleteNote(Integer noteId);

    void createNote(NoteDto noteDto);

    List<String> listFio();
}
