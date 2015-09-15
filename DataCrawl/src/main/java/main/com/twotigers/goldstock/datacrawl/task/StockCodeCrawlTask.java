package main.com.twotigers.goldstock.datacrawl.task;

import main.com.twotigers.goldstock.datacrawl.framework.BaseCrawlTask;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/9/13.
 */
@Component
public class StockCodeCrawlTask extends BaseCrawlTask {

    /**
     * 每天晚上9点半触发
     */
    //private final static String CRON_EXPR = "0 30 21 ? * *";
    private final static String CRON_EXPR = "/5 * * ? * *";

    @Override
    public void run() {
        System.out.println("股票代码正在抓取...");
        System.out.println("股票代码抓取完毕.");
    }

    @Override
    public String getTaskName() {
        return "股票代码抓取";
    }

    @Override
    public String getScheduleExpr() {
        return CRON_EXPR;
    }
}
