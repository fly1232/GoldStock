package main.com.twotigers.goldstock.datacrawl.task.stockquote;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import main.com.twotigers.goldstock.datacrawl.cache.StockCodeCache;
import main.com.twotigers.goldstock.datacrawl.common.Constants;
import main.com.twotigers.goldstock.datacrawl.common.BaseCrawlTask;
import main.com.twotigers.goldstock.datacrawl.common.TaskException;
import main.com.twotigers.goldstock.datacrawl.utils.DateUtity;
import main.com.twotigers.goldstock.datacrawl.utils.DbUtity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2015/9/19.
 * 股票行情抓取
 */
public class StockQuoteCrawlTask extends BaseCrawlTask {

    private final static Log logger = LogFactory.getLog(StockQuoteCrawlTask.class);

    @Override
    protected void process() throws TaskException {
        try {
            List<String> stockCodes = StockCodeCache.getStockCodes();
            int len = stockCodes.size();
            BlockingQueue<Runnable> secuCodeQueue = new LinkedBlockingQueue<Runnable>(100);
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, secuCodeQueue);
            threadPoolExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    logger.info("SecuCode Rejected : " + ((StockQuoteCrawlUnitTask) r).getStockCode());
                    try {
                        boolean bEnter = false;
                        while (!bEnter) {
                            logger.info("Waiting for 3000 ms !!");
                            Thread.sleep(3000);
                            bEnter = secuCodeQueue.offer(r);
                        }
                        logger.info("SecuCode accepted : " + ((StockQuoteCrawlUnitTask) r).getStockCode());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Let start all core threads initially
            threadPoolExecutor.prestartAllCoreThreads();

            AtomicInteger rowIndex = new AtomicInteger(0);
            int stockCount = stockCodes.size();
            for (String stockCode: stockCodes) {
                StockQuoteCrawlUnitTask unitTask = new StockQuoteCrawlUnitTask(stockCode);
                unitTask.setRowIndex(rowIndex);
                unitTask.setStockCount(stockCount);
                threadPoolExecutor.execute(unitTask);
            }

            while (threadPoolExecutor.getActiveCount()> 0 ){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            threadPoolExecutor.shutdown();
        }
        catch (Exception ex){
            throw new TaskException(ex);
        }
    }

    @Override
    public String getTaskName() {
        return "day quote crawl";
    }

    @Override
    public String getScheduleExpr() {
        return null;
    }
}
