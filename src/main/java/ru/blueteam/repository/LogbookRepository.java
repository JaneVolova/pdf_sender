package ru.blueteam.repository;

import ru.blueteam.model.Note;

import java.time.LocalDate;
import java.util.List;

public interface LogbookRepository {
    void save(Note note);
    List<Note> findAllByDay(LocalDate date);

}
