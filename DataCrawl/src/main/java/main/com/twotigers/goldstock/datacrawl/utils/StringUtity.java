package main.com.twotigers.goldstock.datacrawl.utils;

/**
 * Created by Administrator on 2015/9/20.
 */
public class StringUtity {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return (str == null)||(str.trim()=="");
    }
}
