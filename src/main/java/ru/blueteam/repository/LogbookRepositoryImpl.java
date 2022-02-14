package ru.blueteam.repository;

import ru.blueteam.model.Note;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class LogbookRepositoryImpl implements LogbookRepository {

    private final DataSource dataSource;

    //language=SQL
    private final String SQL_SAVE_NOTE = "insert into logbook(user_id, description) values(?,?)";

    //language=SQL
    private final String SQL_FIND_NOTES_BY_DAY = "select * from logbook where date = ?";

    public LogbookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
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
                note.setNoteId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't get id");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Note> findNotesByDay(Date date) {
        List<Note> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_NOTES_BY_DAY)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(noteMapper.apply(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
