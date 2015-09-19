package main.com.twotigers.goldstock.datacrawl.common;

/**
 * Created by Administrator on 2015/9/15.
 */
public class Constants {
    /**
     * 数据库名
     */
    public final static String DATABASE_NAME = "goldstock";

    /**
     * 表名：股票代码
     */
    public final static String TABLE_NAME_STOCKCODE = "stockcode";

    /**
     * 上海股票市场
     */
    public final static String MARKET_STOCK_SHA = "sha";

    /**
     * 深圳股票市场
     */
    public final static String MARKET_STOCK_SZA = "sza";

    /**
     * 支持的市场列表
     */
    public final static String[] MARKET_NAME_ARR = new String[]{ MARKET_STOCK_SHA, MARKET_STOCK_SZA };

}
