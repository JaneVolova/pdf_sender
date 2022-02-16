package ru.blueteam.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;
import ru.blueteam.scheduler.SendScheduler;

public class Main {
    private static final String DB_USERS = "postgres";
    private static final String DB_PASSWORD = "28679854";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/pdfsender";

    public static void main(String[] args) {


        HikariConfig config = new HikariConfig();
        config.setUsername(DB_USERS);
        config.setPassword(DB_PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(20);

        HikariDataSource dataSource = new HikariDataSource(config);

        LogbookRepository logbookRepository = new LogbookRepositoryImpl(dataSource);
//        UserRepository userRepository = new UserRepositoryImpl(dataSource);

        Note note = Note.builder()
                .userId(1L)
                .description("за сегодня сделано много полезных дел!")
                .build();
        logbookRepository.save(note);

        System.out.println(logbookRepository.findById(1L).toString());


    }
}
