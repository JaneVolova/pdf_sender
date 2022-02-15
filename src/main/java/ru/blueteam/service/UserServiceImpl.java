package ru.blueteam.service;

import ru.blueteam.model.User;
import ru.blueteam.repository.UserRepository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
    }

    /**
     *
     * у меня большие сомнения насчет корректности работы метода save()
     * хотела захэшировать пароль.
     * Забыла!! надо раскодировать пароль при поиске пользователя. Или нам не надо их искать?
     * Если будем хэшировать, то узнать длину самого хэша пароля, может надо
     * увеличить лимит поля password в базе данных
     */
    @Override
    public void save(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {

//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//
//        KeySpec spec = new PBEKeySpec(user.getPassword().toCharArray(), salt, 65536, 128);
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("secret_key");
//
//        byte[] hash = factory.generateSecret(spec).getEncoded();

        User newUser = User.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .build();
        userRepository.save(newUser);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
