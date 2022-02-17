package ru.blueteam.repository;

import ru.blueteam.model.Student;

import java.util.List;

public interface StudentRepository {
    Student findById(Integer studentId);
    List<Student> findAll();

}
