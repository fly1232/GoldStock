package main;

import main.com.twotigers.goldstock.datacrawl.job.CrawlJobDetail;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Created by xuyufei on 2015/9/12.
 * develop
 */
public class App {
    /**
     * 程序主方法
     * @param args 主函数参数，目前暂未使用
     */
    private static Scheduler sched = null;
    public static void main( String[] args ) {
        try {
            try {
                sched = initSchedule();
                Thread.sleep(1000);
            }
            catch (Exception e) {
                //调度器停止运行
                if (sched!=null) {
                    sched.shutdown(true);
                }
            }
            System.out.println("程序正在运行...");

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private static Scheduler initSchedule() throws SchedulerException {
        // 通过SchedulerFactory获取一个调度器实例
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        //创建任务
        JobDetail dailyJob = JobBuilder.newJob(CrawlJobDetail.class)
                .withIdentity("定时抓取任务")
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

        //注册调度任务
        sched.scheduleJob(dailyJob, trigger);
        //启动任务调度
        sched.start();

        return sched;
    }
}
