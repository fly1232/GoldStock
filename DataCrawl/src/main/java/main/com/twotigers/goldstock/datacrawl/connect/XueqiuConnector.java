package main.com.twotigers.goldstock.datacrawl.connect;

import main.com.twotigers.goldstock.datacrawl.utils.HttpRequester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/19.
 */
public class XueqiuConnector {

    private final static String STR_COOKIE_XUEQIU = "bid=9b15a2aa90149bcac4ad19830e783e9d_i77n2wc5; s=99d1vonc3l; xq_a_token=57b3a250eeedbb43ab231666cdb8fa1aab3a7bea; xqat=57b3a250eeedbb43ab231666cdb8fa1aab3a7bea; xq_r_token=244e4f2f9d234a41f9e7a5a34b582ba90d8dc970; xq_is_login=1; u=4330033544; xq_token_expire=Wed%20Oct%2014%202015%2001%3A22%3A52%20GMT%2B0800%20(CST); __utma=1.1649449937.1427454658.1442596625.1442599223.160; __utmb=1.1.10.1442599223; __utmc=1; __utmz=1.1442115445.152.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; Hm_lvt_1db88642e346389874251b5a1eded6e3=1442412275,1442507370,1442592091,1442596626; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1442599223";

    /**
     * 发送GET请求
     *
     * @param urlString
     *            URL地址
     * @param params
     *            参数集合
     * @param properties
     *            请求属性
     * @return 响应对象
     * @throws IOException
     */
    public static String sendGet(String urlString, Map<String, String> params,
                          Map<String, String> properties) throws IOException {
        HttpRequester httpRequester = new HttpRequester();
        if (properties==null){
            properties = new HashMap<String, String>();
        }
        properties.put("Cookie", STR_COOKIE_XUEQIU);
        return httpRequester.sendGet(urlString, params, properties);
    }
}
