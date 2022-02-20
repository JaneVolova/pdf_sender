package ru.blueteam.service;

import ru.blueteam.model.Student;
import ru.blueteam.repository.StudentsRepository;

import java.util.List;


public class StudentServiceImpl implements StudentService {
    private StudentsRepository studentsRepository;

    @Override
    public List<Student> studentList() {
        return studentsRepository.listName();
    }
}
