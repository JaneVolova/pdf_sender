package ru.blueteam.repository;

import ru.blueteam.model.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    List<User> findAll();
}
