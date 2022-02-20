package ru.blueteam.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.IOException;

public class SenderJob implements Job {
    private LogbookService logbookService;
    private final String TELEGRAM_ULR = "http://34.118.12.76:8080/TelegramService-1.0-SNAPSHOT/sendPDF";
    private final String MAIL_URL = "http://34.118.35.238:8080/emailSender/EmailServlet";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {


        String json = ConvertService.convertListToJson(logbookService.findAllNotesByDay());
        try {
            HttpPostService.sendJsonByUrl(TELEGRAM_ULR, json);
            HttpPostService.sendJsonByUrl(MAIL_URL, json);
        } catch (IOException e) {
           System.err.println("Ошибка отправки на стороннее приложение");
        }
    }
}
