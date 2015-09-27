package main;

import main.com.twotigers.goldstock.datacrawl.common.MongoDbConfigTool;
import main.com.twotigers.goldstock.datacrawl.job.CrawlJobDetail;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.util.TimeZone;

import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Created by xuyufei on 2015/9/12.
 * develop
 */
public class DataCrawlApp {
    /**
     * program entry
     * @param args
     */
    private static Scheduler sched = null;
    public static void main( String[] args ) {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            doPrepare();
            sched = initSchedule();
            Thread.sleep(1000);
//            //调度器停止运行
//            if (sched!=null) {
//                sched.shutdown(true);
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The program is running...");
    }

    private static void doPrepare() throws IOException {
        MongoDbConfigTool.initMongoDbConfig();
    }

    private static Scheduler initSchedule() throws SchedulerException {
        // 通过SchedulerFactory获取一个调度器实例
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        //创建任务
        JobDetail dailyJob = JobBuilder.newJob(CrawlJobDetail.class)
                .withIdentity("timing crawl job")
                .build();
        //创建触发器, 定时触发
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInHours(6)
                .repeatForever();
        SimpleTrigger trigger = TriggerBuilder
                .newTrigger()
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(simpleScheduleBuilder)
                .build();

        //register job
        sched.scheduleJob(dailyJob, trigger);
        //start job
        sched.start();

        return sched;
    }
}
