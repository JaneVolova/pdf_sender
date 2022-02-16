package ru.blueteam.repository;

import ru.blueteam.model.Note;

import java.util.List;

public interface LogbookRepository {

    void updateNote(Note note);

    Note findById(Integer note_id);

    List<Note> findAllNotes();
}
