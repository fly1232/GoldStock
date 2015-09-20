package test.main;

import com.mongodb.DBObject;
import junit.framework.Assert;
import junit.framework.TestCase;
import main.com.twotigers.goldstock.datacrawl.task.stockquote.StockQuoteCrawlTask;
import main.com.twotigers.goldstock.datacrawl.task.stockquote.StockQuoteDownloader;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
public class QuoteDownloadTestCase extends TestCase {

    public void testDownloadQuote(){
        String errMsg = null;
        try {
            String symbol = "SH600239";
            List<DBObject> insertObjs = StockQuoteDownloader.getQuoteObjs(symbol, null);
        }
        catch (Exception e){
            errMsg = e.toString() + e.getMessage();
        }
        Assert.assertNull(errMsg, errMsg);
    }
}
