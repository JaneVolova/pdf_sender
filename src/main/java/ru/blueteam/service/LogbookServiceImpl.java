package ru.blueteam.service;

import ru.blueteam.dto.NoteForm;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;

import java.util.Date;
import java.util.List;

public class LogbookServiceImpl implements LogbookService {

    private LogbookRepository logbookRepository;

    public LogbookServiceImpl(LogbookRepository logbookRepository) {
        this.logbookRepository = logbookRepository;
    }

    @Override
    public void createNote(NoteForm form) {
        Note note = Note.builder()
                .userId(form.getUserId())
                .date(form.getDate()) // здесь надо установить такущую дату
                .description(form.getDescription())
                .isDeleted(false)
                .build();

        logbookRepository.save(note);
    }

    @Override
    public List<Note> listNodesByDate(Date date) {
        return logbookRepository.findNotesByDate(date);
    }

    @Override
    public List<Note> listNodesByUser(Long userId) {
        return logbookRepository.findNotesByUser(userId);
    }

    @Override
    public void deleteNote(Long noteId) {
        logbookRepository.deleteNoteById(noteId);
    }

    @Override
    public void updateNote(Note note) {
        logbookRepository.updateNote(note);
    }

    @Override
    public boolean isDeleted(Long noteId) {
        return logbookRepository.findById(noteId) == null;
    }

}