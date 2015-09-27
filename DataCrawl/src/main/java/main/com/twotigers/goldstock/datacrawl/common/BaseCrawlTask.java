package main.com.twotigers.goldstock.datacrawl.common;

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
            logger.info(String.format("Task:[%s] is beginning...", getTaskName()));
            long startTime = System.currentTimeMillis();
            process();
            long hastTime = System.currentTimeMillis() - startTime;
            logger.info(String.format("Task:[%s] is finished，and hasted %d ms", getTaskName(), hastTime));
        } catch (Exception e) {
            logger.error("A error occured when running, the detail cause is :" + e.getMessage(), e);
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
