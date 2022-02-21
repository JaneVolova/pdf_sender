package ru.blueteam.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import ru.blueteam.repository.LogbookRepository;
import ru.blueteam.repository.LogbookRepositoryImpl;

import java.io.IOException;

public class SenderJob implements Job {
    private HikariDataSource dataSource;
    private LogbookService logbookService;

    private final String TELEGRAM_ULR = "http://34.118.12.76:8080/TelegramService-1.0-SNAPSHOT/sendPDF";
    private final String MAIL_URL = "http://34.118.35.238:8080/emailSender/EmailServlet";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername("sender");
        hikariConfig.setPassword("strongpassword");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://34.116.245.1:5432/intensivedb");
        hikariConfig.setMaximumPoolSize(2);

        System.out.println("Database connection inits success");
        dataSource = new HikariDataSource(hikariConfig);
        LogbookRepository logbookRepository = new LogbookRepositoryImpl(dataSource);
        this.logbookService = new LogbookServiceImpl(logbookRepository);
        String json = ConvertService.convertListToJson(logbookService.findAllNotesByDay());
        System.out.println(json);
        try {
            HttpPostService.sendJsonByUrl(TELEGRAM_ULR, json);
            HttpPostService.sendJsonByUrl(MAIL_URL, json);
        } catch (IOException e) {
            System.err.println("Ошибка отправки на стороннее приложение");
        }
        dataSource.close();
    }
}
