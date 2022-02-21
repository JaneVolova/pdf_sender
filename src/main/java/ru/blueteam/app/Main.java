package ru.blueteam.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.quartz.SchedulerException;
import ru.blueteam.sheduler.SendScheduler;

public class Main {
    private static final String DB_USERS = "sender";
    private static final String DB_PASSWORD = "strongpassword";
    private static final String DB_URL = "jdbc:postgresql://34.116.245.1:5432/intensivedb";

    public static void main(String[] args) throws SchedulerException {

        HikariConfig config = new HikariConfig();
        config.setUsername(DB_USERS);
        config.setPassword(DB_PASSWORD);
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(DB_URL);
        config.setMaximumPoolSize(20);

        HikariDataSource dataSource = new HikariDataSource(config);

//        LogbookRepository logbookRepository = new LogbookRepositoryImpl(dataSource);

        SendScheduler.init();
    }
}
