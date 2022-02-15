package ru.blueteam.repository;

import ru.blueteam.model.User;
import ru.blueteam.service.UserService;
import ru.blueteam.service.UserServiceImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;
    private UserService userService;

    //language=SQL
    private final String SQL_SAVE_USER = "insert into users(name, password) values(?,?)";

    //language=SQL
    private final String SQL_FIND_ALL_USERS = "select * from users ";

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        UserService userService = new UserServiceImpl(this);
    }

    private static final Function<ResultSet, User> userMapper = resultSet -> {
        try {
            return User.builder()
                    .id(resultSet.getLong("user_id"))
                    .name(resultSet.getString("name"))
                    .password(resultSet.getString("password"))
                    .build();

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };

    @Override
    public void save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Can't save user");
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("Can't get id");
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(userMapper.apply(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
