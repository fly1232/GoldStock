package main.com.twotigers.goldstock.datacrawl.task.stockquote;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import main.com.twotigers.goldstock.datacrawl.common.Constants;
import main.com.twotigers.goldstock.datacrawl.utils.DbUtity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2015/9/27.
 * 处理单个股票
 */
public class StockQuoteCrawlUnitTask implements Runnable {

    private final static Log logger = LogFactory.getLog(StockQuoteCrawlUnitTask.class);

    /**
     * 股票代码
     */
    private String stockCode;

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    /**
     * 股票总数
     */
    private Integer stockCount ;

    /**
     * 当前进度
     */
    private AtomicInteger rowIndex ;

    public String getStockCode() {
        return stockCode;
    }

    public void setRowIndex(AtomicInteger rowIndex) {
        this.rowIndex = rowIndex;
    }

    public StockQuoteCrawlUnitTask(String stockCode){
        this.stockCode = stockCode;
    }

    @Override
    public void run() {
        int row = rowIndex.incrementAndGet();
        logger.info(String.format("Day quote crawl: %d/%d, %s...", row, stockCount, stockCode));
        try {
            fetchQuoteForEachStock(stockCode);
            logger.info(String.format("Day quote crawl: %s ...ok.", stockCode));
        } catch (IOException e) {
            logger.info(String.format("Day quote crawl: %s ...failure, the detail cause is:" + e.getMessage(), stockCode), e);
        }

    }

    private void fetchQuoteForEachStock(String stockCode) throws IOException {
        String maxDate = getMaxDateOfStock(stockCode);
        String listDate = getListDateOfStock(stockCode);
        List<DBObject> insertObjs = StockQuoteDownloader.getQuoteObjs(stockCode, listDate, maxDate);
        if ((insertObjs!=null)&&!insertObjs.isEmpty()) {
            MongoClient mongoClient = DbUtity.createMongoClient();
            DbUtity.insertDbObjects(mongoClient, Constants.TABLE_NAME_DAY_QUITE, insertObjs);
            mongoClient.close();
        }
    }

    /**
     *  获取行情表中，某个股票的最大日期
     * @param stockCode
     * @return
     * @throws UnknownHostException
     */
    private String getMaxDateOfStock(String stockCode) throws UnknownHostException {
        MongoClient mongoClient = null;
        try {
            mongoClient = DbUtity.createMongoClient();
            return (String)DbUtity.getMaxMinFieldValueOfTable(mongoClient,
                    Constants.TABLE_NAME_DAY_QUITE,
                    new BasicDBObject("symbol", stockCode),
                    "date",
                    true);
        }
        finally {
            if (mongoClient != null)
                mongoClient.close();
        }
    }

    /**
     * 获取股票的上市日期
     * @param stockCode
     * @return
     * @throws UnknownHostException
     */
    private String getListDateOfStock(String stockCode) throws UnknownHostException {
        MongoClient mongoClient = null;
        try {
            mongoClient = DbUtity.createMongoClient();
            return (String)DbUtity.getFieldValueOfTable(mongoClient,
                    Constants.TABLE_NAME_STOCK_CODE,
                    new BasicDBObject("symbol", stockCode),
                    "list_date");
        }
        finally {
            if (mongoClient != null)
                mongoClient.close();
        }
    }
}
