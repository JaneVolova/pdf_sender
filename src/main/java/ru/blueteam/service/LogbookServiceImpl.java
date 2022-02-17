package ru.blueteam.service;

import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;

import java.util.List;

public class LogbookServiceImpl implements LogbookService {

    private final LogbookRepository logbookRepository;

    public LogbookServiceImpl(LogbookRepository logbookRepository) {
        this.logbookRepository = logbookRepository;
    }

    @Override
    public void updateNote(Note note) {
        logbookRepository.updateNote(note);
    }

    @Override
    public Note findById(Integer noteId) {
        return logbookRepository.findById(noteId);
    }

    @Override
    public List<Note> findAllNotes() {
        return logbookRepository.findAllNotes();
    }
}