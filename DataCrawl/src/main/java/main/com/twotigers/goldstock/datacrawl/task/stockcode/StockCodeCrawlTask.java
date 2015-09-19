package main.com.twotigers.goldstock.datacrawl.task.stockcode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import main.com.twotigers.goldstock.datacrawl.common.Constants;
import main.com.twotigers.goldstock.datacrawl.connect.XueqiuConnector;
import main.com.twotigers.goldstock.datacrawl.framework.BaseCrawlTask;
import main.com.twotigers.goldstock.datacrawl.utils.DateUtity;
import main.com.twotigers.goldstock.datacrawl.utils.DbUtity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Administrator on 2015/9/13.
 */
@Component
public class StockCodeCrawlTask extends BaseCrawlTask {

    private final static Log logger = LogFactory.getLog(StockCodeCrawlTask.class);

    /**
     * 每天晚上9点半触发
     */
    //private final static String CRON_EXPR = "0 30 21 ? * *";
    private final static String CRON_EXPR = "/5 * * ? * *";

    @Override
    public void run() {
        try {
            logger.info("股票代码正在抓取...");
            for (String marketName : Constants.MARKET_NAME_ARR) {
                procMarket(marketName);
            }
            logger.info("股票代码抓取完毕.");
        } catch (Exception e) {
            logger.error("运行任务时发生错误，原因是" + e.getMessage(), e);
        }
    }

    @Override
    public String getTaskName() {
        return "股票代码抓取";
    }

    @Override
    public String getScheduleExpr() {
        return CRON_EXPR;
    }

    private void procMarket(String marketName) throws IOException, ParseException {
        //目前只处理两个市场，沪A和深A

        long countInMongo = getCountOfMongo(marketName);
        long countFromWeb = getCountFromWeb(marketName);
        if (countFromWeb == countInMongo) {
            logger.info(String.format("市场：%s 没有更新，无需抓取.", marketName));
        }
        else {
            logger.info(String.format("市场：%s 发现最新数据，即将抓取...", marketName));
            fetchDataFromWeb(marketName, countFromWeb - countInMongo);
            logger.info(String.format("市场：%s 抓取完毕.", marketName));
        }
    }
    private void fetchDataFromWeb(String marketName, long fetchCount ) throws IOException, ParseException {
        if (fetchCount <= 0) return;

        int pageSize = 90;
        int pageCount = (int) Math.ceil( fetchCount * 1.0 / pageSize );
        logger.info(String.format("共需抓取%d条记录，以%d条为一页，共需下载%d个页面.", fetchCount, pageSize, pageCount));
        for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
            logger.info(String.format("正在下载第%d页...", pageNum));
            String requestUrl = "http://xueqiu.com/proipo/query.json";
            Map<String, String> paramaters = new HashMap<String, String>();
            paramaters.put("page", String.valueOf(pageNum));
            paramaters.put("size", String.valueOf(pageSize));
            paramaters.put("order", "desc");
            paramaters.put("column", "symbol,name,list_date");
            paramaters.put("orderBy", "list_date");
            paramaters.put("type", "quote");
            paramaters.put("stockType", marketName);
            String jsonStr = XueqiuConnector.sendGet(requestUrl, paramaters, null);
            logger.info(String.format("第%d页下载完成.", pageNum));

            logger.info(String.format("解析第%d页数据，并写入数据库...", pageNum));
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<DBObject> dbObjects = new ArrayList<>(jsonArray.size());
            for (Object obj : jsonArray) {
                JSONArray fieldArr = (JSONArray) obj;
                DBObject dbObject = new BasicDBObject();
                dbObject.put("symbol", fieldArr.get(0));
                dbObject.put("name", fieldArr.get(1));
                Date listDate = DateUtity.parseXueqiuFormatToDate(fieldArr.get(2).toString());
                dbObject.put("list_date", DateUtity.dateToDateStr(listDate));
                dbObject.put("market", marketName);
                dbObjects.add(dbObject);
            };

            MongoClient mongoClient = DbUtity.createMongoClient();
            DbUtity.insertDbObjects(mongoClient, Constants.TABLE_NAME_STOCKCODE, dbObjects);
            mongoClient.close();
            logger.info(String.format("完成解析第%d页数据并写入数据库.", pageNum));
        }
    }

    private long getCountOfMongo(String marketName) throws UnknownHostException {
        MongoClient mongoClient = null;
        try {
            mongoClient = DbUtity.createMongoClient();
            DbUtity.createIndexForTable(mongoClient,
                    Constants.TABLE_NAME_STOCKCODE,
                    new BasicDBObject("market", 1));
            return DbUtity.getRowCountOfTable(mongoClient,
                    Constants.TABLE_NAME_STOCKCODE,
                    new BasicDBObject("market", marketName));
        }
        finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    private long getCountFromWeb(String marketName) throws IOException {
        String requestUrl = "http://xueqiu.com/proipo/query.json";
        Map<String,String> paramaters = new HashMap<String, String>();
        paramaters.put("page", "1");
        paramaters.put("size", "30");
        paramaters.put("order", "asc");
        paramaters.put("column", "symbol");
        paramaters.put("orderBy", "list_date");
        paramaters.put("type", "quote");
        paramaters.put("stockType",marketName);

        String jsonStr = XueqiuConnector.sendGet(requestUrl, paramaters, null);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return (long)jsonObject.getDouble("count").doubleValue();
    }
}
