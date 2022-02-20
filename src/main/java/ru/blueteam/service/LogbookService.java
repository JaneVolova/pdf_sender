package ru.blueteam.service;

import ru.blueteam.dto.NoteDto;
import ru.blueteam.model.Note;

import java.util.List;

public interface LogbookService {

    void updateNote(Note note);

    Note findById(Integer noteId);

    List<Note> findAllNotesByDay();

    void deleteNote(Integer noteId);

    void createNote(NoteDto noteDto);

    List<Note> findAllNotesByStudent(String fio);

    List<String> listName();

}
