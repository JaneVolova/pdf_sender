package ru.blueteam.sheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.blueteam.service.SenderJob;

public class SendScheduler {
    public static void init() {
        JobDetail jobDetail = JobBuilder.newJob(SenderJob.class).withIdentity("service.SenderJob", "group1").build();
//TODO: выставить боевой cron, убрать коментарии
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 05 10 1/1 * ? *"))
                .build();
        /*
         * CRON выражения для тестирования
         * "0 0/1 * 1/1 * ? *" каждую минуту
         * "0 0/5 * 1/1 * ? *" каждые 5 минут
         * "0 30 14 1/1 * ? *" каждый день в 14:30 (у сервера время -3 часа от МСК)
         */

        SchedulerFactory sFactory = new StdSchedulerFactory();
        Scheduler scheduler;

        try {
            scheduler = sFactory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
