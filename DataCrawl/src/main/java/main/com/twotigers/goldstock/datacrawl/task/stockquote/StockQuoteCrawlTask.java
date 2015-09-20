package main.com.twotigers.goldstock.datacrawl.task.stockquote;

import com.alibaba.fastjson.JSONArray;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import main.com.twotigers.goldstock.datacrawl.cache.StockCodeCache;
import main.com.twotigers.goldstock.datacrawl.common.Constants;
import main.com.twotigers.goldstock.datacrawl.framework.BaseCrawlTask;
import main.com.twotigers.goldstock.datacrawl.framework.TaskException;
import main.com.twotigers.goldstock.datacrawl.utils.DbUtity;
import main.com.twotigers.goldstock.datacrawl.utils.HttpDownloader;
import main.com.twotigers.goldstock.datacrawl.utils.StringUtity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SimpleTrigger;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
            int index = 0;
            for (String stockCode: stockCodes) {
                logger.info(String.format("抓取股票行情: %d/%d, %s...", ++index, len, stockCode));
                fetchQuoteForEachStock(stockCode);
                logger.info(String.format("抓取股票行情: %s ...执行完毕.", stockCode));
            }
        }
        catch (Exception ex){
            throw new TaskException(ex);
        }
    }

    private void fetchQuoteForEachStock(String stockCode) throws IOException {
        String maxDate = getMaxDateOfStock(stockCode);
        List<DBObject> insertObjs = StockQuoteDownloader.getQuoteObjs(stockCode, maxDate);
        if (!insertObjs.isEmpty()) {
            MongoClient mongoClient = DbUtity.createMongoClient();
            DbUtity.insertDbObjects(mongoClient, Constants.TABLE_NAME_STOCK_QUITE, insertObjs);
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
            return (String)DbUtity.getMaxMinFieldValuesOfTable( mongoClient,
                                Constants.TABLE_NAME_STOCK_QUITE,
                                new BasicDBObject("symbol", stockCode),
                                "date",
                                true );
        }
        finally {
            if (mongoClient != null)
                mongoClient.close();
        }
    }

    @Override
    public String getTaskName() {
        return "股票行情抓取";
    }

    @Override
    public String getScheduleExpr() {
        return null;
    }
}
