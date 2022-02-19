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
    private final String SQL_FIND_ALL_NOTES_BY_DAY = "select * from logbook where date = ? order by student_id";
    private final String SQL_FIND_ALL_NOTES_BY_STUDENT = "select * from logbook where student_id = ?";
    private final String SQL_FIND_NOTES_BY_ID = "select * from logbook where logbook_id = ?";
    private final String SQL_UPDATE_NOTE = "update logbook set student_id = ?, description = ? where logbook_id = ?";
    private final String SQL_DELETE_NOTE = "delete from logbook where logbook_id = ?";
    private final String SQL_CREATE_NOTE = "insert into logbook (student_id, description) values (?,?)";


    public LogbookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        LogbookService logbookService = new LogbookServiceImpl(this);
    }

    private static final Function<ResultSet, Note> noteMapper = resultSet -> {
        try {
            return Note.builder()
                    .noteId(resultSet.getInt("logbook_id"))
                    .studentId(resultSet.getInt("student_id"))
                    .date(resultSet.getDate("date").toLocalDate())
                    .description(resultSet.getString("description"))
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
    public List<Note> findAllNotesByStudent(Integer studentId) {
        List<Note> notesByUser = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_NOTES_BY_STUDENT)) {
            statement.setInt(1, studentId);

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

            statement.setInt(1, note.getStudentId());
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

            statement.setInt(1, noteDto.getStudentId());
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


}
