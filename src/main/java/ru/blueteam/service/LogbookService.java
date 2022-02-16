package ru.blueteam.service;

import ru.blueteam.model.Note;

import java.util.List;

public interface LogbookService {

    void updateNote(Note note);

    Note findById(Integer noteId);

    List<Note> findAllNotes();

}
