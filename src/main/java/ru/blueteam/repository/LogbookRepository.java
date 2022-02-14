package ru.blueteam.repository;

import ru.blueteam.model.Note;

import java.util.Date;
import java.util.List;

public interface LogbookRepository {

    void save(Note note); //сохранить запись

    void deleteNoteById(Long noteId);

    void updateNote(Note note);

    Note findById(Long noteId);

    List<Note> findNotesByDate(Date date); // найти список записей за день

    List<Note> findNotesByUser(Long userId); // найти список записей одного юзера
}
