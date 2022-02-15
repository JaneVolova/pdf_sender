package ru.blueteam.service;

import ru.blueteam.dto.NoteForm;
import ru.blueteam.model.Note;

import java.util.Date;
import java.util.List;

public interface LogbookService {

    void createNote(NoteForm form);

    List<Note> listNodesByDate(Date date);

    List<Note> listNodesByUser(Long userId);

    void deleteNote(Long noteId);

    void updateNote(Note note);

    boolean isDeleted(Long noteId);

}
