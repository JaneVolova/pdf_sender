package ru.blueteam.service;

import ru.blueteam.dto.NoteDto;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;

import java.time.LocalDate;
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
        return logbookRepository.findNoteById(noteId);
    }

    @Override
    public List<Note> findAllNotesByDay() {
        LocalDate date = LocalDate.now();
        return logbookRepository.findAllNotesByDay(date);
    }

    @Override
    public void deleteNote(Integer noteId) {
        logbookRepository.deleteNote(noteId);
    }

    @Override
    public void createNote(NoteDto noteDto) {
        logbookRepository.createNote(noteDto);
    }

    @Override
    public List<Note> findAllNotesByStudent(String fio) {
        return logbookRepository.findAllNotesByStudent(fio);
    }

    @Override
    public List<String> listName() {
        return logbookRepository.listFio();
    }
}