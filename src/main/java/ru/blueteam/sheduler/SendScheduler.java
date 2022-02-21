package ru.blueteam.sheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.blueteam.service.SenderJob;

public class SendScheduler {
    public static void init() {
        JobDetail jobDetail = JobBuilder.newJob(SenderJob.class).withIdentity("service.SenderJob", "group1").build();
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 30 14 1/1 * ? *"))
                .build();

        SchedulerFactory sFactory = new StdSchedulerFactory();
        Scheduler scheduler;

        try {
            scheduler = sFactory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
           System.err.println("Scheduler start error");
        }
    }
}
