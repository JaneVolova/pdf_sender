package ru.blueteam.service;

import ru.blueteam.model.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface UserService {
    void save(User user) throws NoSuchAlgorithmException, InvalidKeySpecException;
    List<User> findAll();
}
