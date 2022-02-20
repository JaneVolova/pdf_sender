package ru.blueteam.repository;

import ru.blueteam.model.Student;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class StudentsRepositoryImpl implements StudentsRepository {
    private final DataSource dataSource;

    public StudentsRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //language=SQL
    private final String SQL_ALL_STUDENTS = "select concat(first_name, ' ',last_name) from students";

    private static final Function<ResultSet, Student> studentsMapper = resultSet -> {
        try {
            return Student.builder()
                    .id(resultSet.getInt("id"))
                    .fio(resultSet.getString("fio"))
                    .build();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    };


    @Override
    public List<Student> listName() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ALL_STUDENTS)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(studentsMapper.apply(resultSet));
                }
                return students;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
