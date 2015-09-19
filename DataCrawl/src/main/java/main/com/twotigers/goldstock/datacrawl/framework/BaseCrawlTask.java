package main.com.twotigers.goldstock.datacrawl.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by Administrator on 2015/9/13.
 * 抓取任务的基础类
 */
public abstract class BaseCrawlTask {

    private final static Log logger = LogFactory.getLog(BaseCrawlTask.class);

    /**
     * 运行方法
     */
    public void run(){
        try {
            logger.info(String.format("任务[%s]正在开始...", getTaskName()));
            long startTime = System.currentTimeMillis();
            process();
            long hastTime = System.currentTimeMillis() - startTime;
            logger.info(String.format("任务[%s]已经完成，消耗时间%d毫秒", getTaskName(), hastTime));
        } catch (Exception e) {
            logger.error("运行任务时发生错误，原因是" + e.getMessage(), e);
        }
    }

    protected abstract void process() throws TaskException;

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
