package ru.blueteam.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;

public class SenderJob implements Job {
    private LogbookService logbookService;
    //TODO: изменить url на валидные, убрать отладочный sout
    private final String TELEGRAM_ULR = "http://34.118.12.76:8080/";
    private final String MAIL_URL = "http://34.118.35.238:8080/";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {


        String json = ConvertService.convertListToJson(logbookService.findAllNotesByDay());
        System.out.println(json);

        System.out.println("Вызов SenderJob по расписанию");
    }
}
