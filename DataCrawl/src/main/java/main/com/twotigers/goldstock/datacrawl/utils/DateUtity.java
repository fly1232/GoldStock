package main.com.twotigers.goldstock.datacrawl.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2015/9/19.
 * 时间工具类
 */
public class DateUtity {

    private final static DateFormat xueqiuFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US );

    public static Date parseXueqiuFormatToDate(String xueqiuDtStr) throws ParseException {
        return xueqiuFormat.parse(xueqiuDtStr);
    }

    private final static DateFormat stdFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String dateToDateStr(Date dt){
        return stdFormat.format(dt);
    }


}
