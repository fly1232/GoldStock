package main.com.twotigers.goldstock.datacrawl.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static Date parseDateStrToDate(String dtStr) throws ParseException {
        return stdFormat.parse(dtStr);
    }

    public static int getYear(Date dt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        return calendar.get(Calendar.YEAR);
    }

    public static int getJidu(Date dt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        return calendar.get(Calendar.MONTH) /3 + 1;
    }


}
