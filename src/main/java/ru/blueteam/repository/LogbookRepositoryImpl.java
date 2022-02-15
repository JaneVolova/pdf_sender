package ru.blueteam.repository;

import ru.blueteam.model.Note;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class LogbookRepositoryImpl implements LogbookRepository { ///rjvv

    private final DataSource dataSource;
    private LogbookService logbookService;
    private LogbookRepository logbookRepository;

    //language=SQL
    private final String SQL_SAVE_NOTE = "insert into logbook(user_id, description) values(?,?)";

    //language=SQL
    private final String SQL_UPDATE_NOTE = "update logbook set date = ?, description = ? where note_id = ?";

    //language=SQL
    private final String SQL_DELETE_NOTE = "update logbook set is_deleted = true where note_id = ?";

    //language=SQL
    private final String SQL_FIND_NOTES_BY_DAY = "select * from logbook where date = ?";

    //language=SQL
    private final String SQL_FIND_NOTES_BY_USER = "select * from logbook where user_id = ?";

    //language=SQL
    private final String SQL_FIND_NOTES_BY_ID = "select * from logbook where logbook_id = ?";


    public LogbookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        LogbookService logbookService = new LogbookServiceImpl(this);
    }

    private static final Function<ResultSet, Note> noteMapper = resultSet -> {
        try {
            return Note.builder()
                    .userId(resultSet.getLong("user_id"))
                    .date(resultSet.getDate("date"))
                    .description(resultSet.getString("description"))
                    .build();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void save(Note note) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_NOTE, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, note.getUserId());
            statement.setString(2, note.getDescription());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save user");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                note.setNoteId(generatedKeys.getLong("logbook_id"));
            } else {
                throw new SQLException("Can't get id");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void deleteNoteById(Long noteId) {
        LogbookService logbookService = new LogbookServiceImpl(this);
        if (logbookRepository.findById(noteId) != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_DELETE_NOTE)) {

                statement.setLong(1, noteId);

                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1) {
                    throw new SQLException("Can't delete note");
                }

            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }


    @Override
    public void updateNote(Note note) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_NOTE)) {

            statement.setString(1, note.getDate().toString());
            statement.setString(2, note.getDescription());
            statement.setLong(3, note.getNoteId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't update user");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Note findById(Long noteId) { // может кто-то переделает на Optional<Note>?
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_NOTES_BY_ID)) {
            statement.setLong(1, noteId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return noteMapper.apply(resultSet);
                } else {
                    throw new IllegalArgumentException("note not found");
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Note> findNotesByDate(Date date) {
        List<Note> notes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_NOTES_BY_DAY)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    notes.add(noteMapper.apply(resultSet));
                }
                return notes;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Note> findNotesByUser(Long userId) {
        List<Note> notesByUser = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_NOTES_BY_USER)) {
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
}
