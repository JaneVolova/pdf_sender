package ru.blueteam.service;

import ru.blueteam.dto.NoteDto;
import ru.blueteam.model.Note;

import java.sql.SQLException;
import java.util.List;

public interface LogbookService {

    void updateNote(Note note);

    Note findById(Integer noteId);

    List<Note> findAllNotesByDay() throws SQLException;

    void deleteNote(Integer noteId);

    void createNote(NoteDto noteDto);

    List<Note> findAllNotesByStudent(String fio);

    List<String> listName();

}

