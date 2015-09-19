package main.com.twotigers.goldstock.datacrawl.job;

import main.com.twotigers.goldstock.datacrawl.framework.BaseCrawlTask;
import main.com.twotigers.goldstock.datacrawl.task.stockcode.StockCodeCrawlTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Administrator on 2015/9/15.
 * 实现job接口
 * 执行所有抓取任务
 */
public final class CrawlJobDetail implements Job {

    private List<BaseCrawlTask> tasks = new ArrayList<BaseCrawlTask>(8);

    public CrawlJobDetail(){
        tasks.add(new StockCodeCrawlTask());
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        tasks.forEach(new Consumer<BaseCrawlTask>() {
            @Override
            public void accept(BaseCrawlTask baseCrawlTask) {
                baseCrawlTask.run();
            }
        });
    }
}
