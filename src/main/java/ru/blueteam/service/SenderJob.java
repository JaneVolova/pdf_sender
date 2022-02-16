package ru.blueteam.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SenderJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {


        //Коннект к базе, сборка Json

        //передача готового Json на TelegramService и EmailService

        System.out.println("Вызов SenderJob по расписанию");
    }
}
