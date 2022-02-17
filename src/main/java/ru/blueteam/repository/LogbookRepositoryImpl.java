package ru.blueteam.repository;

import ru.blueteam.model.Note;
import ru.blueteam.service.LogbookService;
import ru.blueteam.service.LogbookServiceImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LogbookRepositoryImpl implements LogbookRepository {

    private final DataSource dataSource;

    //language=SQL
    private final String SQL_UPDATE_NOTE = "update logbook set date = ?, description = ? where logbook_id = ?";

    //language=SQL
    private final String SQL_FIND_NOTES_BY_ID = "select * from logbook where logbook_id = ?";

    //language=SQL
    private final String SQL_FIND_ALL_NOTES = "select * from logbook order by logbook_id";


    public LogbookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        LogbookService logbookService = new LogbookServiceImpl(this);
    }

    private static final Function<ResultSet, Note> noteMapper = resultSet -> {
        try {
            return Note.builder()
                    .noteId(resultSet.getInt("logbook_id"))
                    .client(resultSet.getString("client"))
                    .date(resultSet.getString("date"))
                    .description(resultSet.getString("description"))
                    .build();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void updateNote(Note note) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_NOTE)) {

            statement.setString(1, note.getDate());
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

    @Override
    public Note findById(Integer noteId) {

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
    public List<Note> findAllNotes() {
        List<Note> notesByUser = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_NOTES)) {
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
