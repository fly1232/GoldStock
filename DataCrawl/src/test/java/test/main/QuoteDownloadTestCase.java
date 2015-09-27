package test.main;

import com.mongodb.DBObject;
import junit.framework.Assert;
import junit.framework.TestCase;
import main.com.twotigers.goldstock.datacrawl.common.MongoDbConfigTool;
import main.com.twotigers.goldstock.datacrawl.task.stockquote.StockQuoteCrawlTask;
import main.com.twotigers.goldstock.datacrawl.task.stockquote.StockQuoteCrawlUnitTask;
import main.com.twotigers.goldstock.datacrawl.task.stockquote.StockQuoteDownloader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2015/9/20.
 */
public class QuoteDownloadTestCase extends TestCase {

    private final static Log logger = LogFactory.getLog(QuoteDownloadTestCase.class);

    public void testDownloadQuote(){
        String errMsg = null;
        try {
            MongoDbConfigTool.initMongoDbConfig();
            String symbol = "SH601003";
            StockQuoteCrawlUnitTask unitTask = new StockQuoteCrawlUnitTask(symbol);
            unitTask.setStockCount(100);
            unitTask.setRowIndex(new AtomicInteger(0));
            unitTask.run();
        }
        catch (Exception e){
            logger.error(e);
            errMsg = e.toString() + e.getMessage();
        }
        Assert.assertNull(errMsg, errMsg);
    }
}
