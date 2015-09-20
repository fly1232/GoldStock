package main.com.twotigers.goldstock.datacrawl.task.stockquote;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import main.com.twotigers.goldstock.datacrawl.utils.HttpDownloader;
import main.com.twotigers.goldstock.datacrawl.utils.StringUtity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 * 股票行情下载器
 */
public class StockQuoteDownloader {

    private final static Log logger = LogFactory.getLog(StockQuoteDownloader.class);

    /**
     * 获取某个股票的行情
     * @param stockCode
     * @param maxDate 返还的行情必须大于maxDate，便于增量下载
     * @return
     * @throws IOException
     */
    public static List<DBObject> getQuoteObjs(String stockCode, final String maxDate) throws IOException {
        final List<DBObject> insertObjs = new ArrayList<DBObject>();
        String url = String.format("http://xueqiu.com/S/%s/historical.csv", stockCode);
        HttpDownloader httpDownloader = new HttpDownloader();
        httpDownloader.downloadTextFile(url, "UTF-8", new HttpDownloader.LineProc() {
            public void proc(String line, boolean isFirstLine) {
                if (!isFirstLine) { //第一行是列标题，不处理
                    try {
                        String[] fieldValues = line.replace("\"", "").split(",");
                        String date = fieldValues[1].substring(0, 10);
                        if ( StringUtity.isEmpty(maxDate)||(date.compareTo(maxDate) > 0)) {
                            DBObject insertObj = new BasicDBObject();
                            insertObj.put("symbol", fieldValues[0]);
                            insertObj.put("date", date);
                            insertObj.put("open", Double.parseDouble(fieldValues[2]));
                            insertObj.put("high", Double.parseDouble(fieldValues[3]));
                            insertObj.put("low", Double.parseDouble(fieldValues[4]));
                            insertObj.put("close", Double.parseDouble(fieldValues[5]));
                            insertObj.put("volume", Double.parseDouble(fieldValues[6]));
                            insertObjs.add(insertObj);
                        }
                    } catch (Exception e){
                        logger.error( "解析时发生错误，line:" + line + ",异常信息是："+ e.getMessage(), e);
                    }
                }
            }
        });
        return insertObjs;
    }

}
