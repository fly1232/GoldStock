package main.com.twotigers.goldstock.datacrawl.job;

import main.com.twotigers.goldstock.datacrawl.common.BaseCrawlTask;
import main.com.twotigers.goldstock.datacrawl.task.stockcode.StockCodeCrawlTask;
import main.com.twotigers.goldstock.datacrawl.task.stockquote.StockQuoteCrawlTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 * implement job interface
 * to run all crawl tasks
 */
public final class CrawlJobDetail implements Job {

    private List<BaseCrawlTask> tasks = new ArrayList<BaseCrawlTask>(8);

    public CrawlJobDetail(){
        tasks.add(new StockCodeCrawlTask());
        tasks.add(new StockQuoteCrawlTask());
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        for (BaseCrawlTask task : tasks){
            task.run();
        }
    }
}
