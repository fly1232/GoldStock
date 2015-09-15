package main.com.twotigers.goldstock.datacrawl.framework;

/**
 * Created by Administrator on 2015/9/13.
 * 抓取任务的基础类
 */
public abstract class BaseCrawlTask {

    /**
     * 运行方法
     */
    public abstract void run();

    /**
     * 任务名称
     * @return
     */
    public abstract String getTaskName();

    /**
     * 执行的定时机制
     */
    public abstract String getScheduleExpr();

}
