package main.com.twotigers.goldstock.datacrawl.task.stockquote;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import main.com.twotigers.goldstock.datacrawl.utils.DateUtity;
import main.com.twotigers.goldstock.datacrawl.utils.HttpDownloader;
import main.com.twotigers.goldstock.datacrawl.utils.StringUtity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
    public static List<DBObject> getQuoteObjs(String stockCode, final String listDate, final String maxDate) throws IOException {
        Date dtNow = new Date();
        if (DateUtity.dateToDateStr(dtNow).equals(maxDate)){//当天已经更新，则无需下载
            return null;
        }

        try {
            List<DBObject> insertObjs = new ArrayList<DBObject>();
            int toYear = DateUtity.getYear(dtNow);
            int toJidu = DateUtity.getJidu(dtNow);

            Date dtList = DateUtity.parseDateStrToDate(listDate);
            int fromYear = DateUtity.getYear(dtList);
            int fromJidu = DateUtity.getJidu(dtList);
            if (maxDate!=null){
                Date dtMaxDate = DateUtity.parseDateStrToDate(maxDate);
                fromYear = DateUtity.getYear(dtMaxDate);
                fromJidu = DateUtity.getJidu(dtMaxDate);
            }
            String urlFormat = "http://vip.stock.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/%s.phtml?year=%d&jidu=%d";
            while ((fromYear*4+fromJidu) <= (toYear * 4 + toJidu)){
                String url = String.format(urlFormat, stockCode.substring(2), fromYear, fromJidu);
                Document doc = null;
                while (doc == null){
                    try {
                        doc = Jsoup.connect(url).timeout(300000).get();
                    }
                    catch (IOException exx){
                        doc = null;
                    }
                }
                Element table = doc.getElementById("FundHoldSharesTable");
                if (table != null) {
                    Elements trs = table.getElementsByTag("tr");
                    for (int index = trs.size() - 1; index >= 2; index--) {
                        Element tr = trs.get(index);
                        Elements tds = tr.getElementsByTag("td");
                        String dtStr = tds.get(0).text().trim();
                        if ((maxDate == null) || (dtStr.compareTo(maxDate) > 0)) {
                            DBObject insertObj = new BasicDBObject();
                            insertObj.put("symbol", stockCode);
                            insertObj.put("date", dtStr);
                            insertObj.put("open", Double.parseDouble(tds.get(1).text().trim()));
                            insertObj.put("high", Double.parseDouble(tds.get(2).text().trim()));
                            insertObj.put("low", Double.parseDouble(tds.get(4).text().trim()));
                            insertObj.put("close", Double.parseDouble(tds.get(3).text().trim()));
                            insertObj.put("volume", Double.parseDouble(tds.get(5).text().trim()));
                            insertObj.put("money", Double.parseDouble(tds.get(6).text().trim()));
                            insertObjs.add(insertObj);
                        }
                    }
                }
                if (fromJidu < 4){
                    fromJidu ++;
                }
                else {
                    fromJidu = 1;
                    fromYear ++;
                }
            }
            return insertObjs;
        } catch (Exception e){
            logger.error( "One error occured when analyzing the code: " + stockCode + ", the detail cause is："+ e.getMessage(), e);
            return null;
        }
    }

}
