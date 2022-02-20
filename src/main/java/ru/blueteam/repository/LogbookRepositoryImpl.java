package ru.blueteam.repository;

import ru.blueteam.dto.NoteDto;
import ru.blueteam.model.Note;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LogbookRepositoryImpl implements LogbookRepository {

    private final DataSource dataSource;

    //language=SQL
    private final String SQL_FIND_ALL_NAMES = "select fio from logbook order by logbook_id";
    private final String SQL_FIND_ALL_NOTES_BY_DAY = "select * from logbook where date = ?";
    //    private final String SQL_FIND_ALL_NOTES_BY_DAY = "select logbook.logbook_id, concat(students.first_name , ' ', students.last_name) as fio, logbook.date, " +
//            "logbook.description from logbook, students where logbook.students_id=students.id and date = ? order by student_id";
    private final String SQL_FIND_ALL_NOTES_BY_STUDENT = "select * from logbook where fio = ?";
    //    private final String SQL_FIND_ALL_NOTES_BY_STUDENT = "select logbook.logbook_id, concat(students.first_name , ' ', students.last_name) as fio, logbook.date, logbook.description from logbook,students where logbook.student_id = students.id and student_id = ? order by logbook_id ;";
    private final String SQL_FIND_NOTES_BY_ID = "select * from logbook where logbook_id = ?";
    //    private final String SQL_FIND_NOTES_BY_ID = "select logbook.logbook_id, concat(students.first_name , ' ', students.last_name) as fio, logbook.date, logbook.description from logbook,students where logbook.student_id = students.id and logbook_id = ?";
    private final String SQL_UPDATE_NOTE = "update logbook set fio = ?, description = ? where logbook_id = ?";
    private final String SQL_DELETE_NOTE = "delete from logbook where logbook_id = ?";
    private final String SQL_CREATE_NOTE = "insert into logbook (fio, description) values (?,?)";


    public LogbookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        LogbookService logbookService = new LogbookServiceImpl(this);
    }

    private static final Function<ResultSet, Note> noteMapper = resultSet -> {
        try {
            return Note.builder()
                    .noteId(resultSet.getInt("logbook_id"))
                    .fio(resultSet.getString("fio"))
                    .date(resultSet.getDate("date").toLocalDate())
                    .description(resultSet.getString("description"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };
    private static final Function<ResultSet, Note> fioMapper = resultSet -> {
        try {
            return Note.builder()
//                    .noteId(resultSet.getInt("logbook_id"))
                    .fio(resultSet.getString("fio"))
//                    .date(resultSet.getDate("date").toLocalDate())
//                    .description(resultSet.getString("description"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };


    // private final String SQL_FIND_ALL_NOTES_BY_DAY = "select * from logbook where date = ? order by student_id";
    @Override
    public List<Note> findAllNotesByDay(LocalDate date) {
        List<Note> notesByUser = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_NOTES_BY_DAY)) {
            statement.setDate(1, Date.valueOf(date));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notesByUser.add(noteMapper.apply(resultSet));
                }
                return notesByUser;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    // private final String SQL_FIND_ALL_NOTES_BY_STUDENT = "select * from logbook where student_id = ?";
    public List<Note> findAllNotesByStudent(String fio) {
        List<Note> notesByUser = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_NOTES_BY_STUDENT)) {
            statement.setString(1, fio);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notesByUser.add(noteMapper.apply(resultSet));
                }
                return notesByUser;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    // private final String SQL_FIND_NOTES_BY_ID = "select * from logbook where logbook_id = ?";
    @Override
    public Note findNoteById(Integer noteId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_NOTES_BY_ID)) {
            statement.setLong(1, noteId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return noteMapper.apply(resultSet);
                } else {
                    throw new IllegalArgumentException("Note not found");
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    //  private final String SQL_UPDATE_NOTE = "update logbook set student_id = ?, description = ? where logbook_id = ?";
    @Override
    public void updateNote(Note note) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_NOTE)) {

            statement.setString(1, note.getFio());
            statement.setString(2, note.getDescription());
            statement.setInt(3, note.getNoteId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't update note");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // private final String SQL_DELETE_NOTE = "delete from logbook where logbook_id = ?";
    @Override
    public void deleteNote(Integer noteId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_NOTE)) {
            statement.setInt(1, noteId);

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't delete note");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void createNote(NoteDto noteDto) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE_NOTE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, noteDto.getFio());
            statement.setString(2, noteDto.getDescription());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't create note");
            }

//            ResultSet generatedKeys = statement.getGeneratedKeys();
//
//            if (generatedKeys.next()) {
//                note.setNoteId(generatedKeys.getInt("id"));
//            } else {
//                throw new SQLException("Can't get id");
//            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<String> listFio() {
        List<String> strings = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_NAMES)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    strings.add(fioMapper.apply(resultSet).getFio());
                }
                return strings;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
