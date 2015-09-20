package main.com.twotigers.goldstock.datacrawl.cache;

import com.mongodb.MongoClient;
import main.com.twotigers.goldstock.datacrawl.common.Constants;
import main.com.twotigers.goldstock.datacrawl.utils.DbUtity;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2015/9/19.
 */
public class StockCodeCache {

    private static Lock lock = new ReentrantLock();

    private static List<String> stockCodes = null;
    public static List<String> getStockCodes() throws UnknownHostException {
        if (stockCodes==null) {
            lock.lock();
            try {
                if (stockCodes==null) {
                    stockCodes = fetchStockCodes();
                }
            } finally {
                lock.unlock();
            }
        }
        return stockCodes;
    }

    private static List<String> fetchStockCodes() throws UnknownHostException {
        MongoClient mongoClient = null;
        try {
            mongoClient = DbUtity.createMongoClient();
            List<String> list = DbUtity.getFieldValuesOfTable(mongoClient,
                    Constants.TABLE_NAME_STOCK_CODE,
                    "symbol");
            return list;
        }
        finally {
            if (mongoClient != null)
                mongoClient.close();
        }
    }
}
