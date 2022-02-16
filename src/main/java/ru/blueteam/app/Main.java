package ru.blueteam.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.blueteam.model.Note;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;

public class Main {
    private static final String DB_USERS = "sender";
    private static final String DB_PASSWORD = "strongpassword";
    private static final String DB_URL = "jdbc:postgresql://34.116.245.1:5432/intensivedb";

    public static void main(String[] args) {

        HikariConfig config = new HikariConfig();
        config.setUsername(DB_USERS);
        config.setPassword(DB_PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(20);

        HikariDataSource dataSource = new HikariDataSource(config);

        LogbookRepository logbookRepository = new LogbookRepositoryImpl(dataSource);

    }
}
