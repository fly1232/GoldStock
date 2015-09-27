package main.com.twotigers.goldstock.datacrawl.connect;

import main.com.twotigers.goldstock.datacrawl.utils.HttpRequester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/19.
 */
public class XueqiuConnector {

    public final static String STR_COOKIE_XUEQIU = "bid=9b15a2aa90149bcac4ad19830e783e9d_i77n2wc5; s=99d1vonc3l; xq_a_token=50f5429ecde84e322c37a7e5ecbb3362cc85f8a5; xqat=50f5429ecde84e322c37a7e5ecbb3362cc85f8a5; xq_r_token=452d61b9252f0e1b614751e9bef141d25f9a53ab; xq_is_login=1; u=4330033544; xq_token_expire=Tue%20Oct%2020%202015%2023%3A26%3A50%20GMT%2B0800%20(CST); __utmt=1; __utma=1.1649449937.1427454658.1443197704.1443258415.172; __utmb=1.5.9.1443258533862; __utmc=1; __utmz=1.1442115445.152.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; Hm_lvt_1db88642e346389874251b5a1eded6e3=1442932463,1442935519,1443194813,1443258415; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1443258534";

    /**
     * send a GET request
     *
     * @param urlString
     *            URL String
     * @param params
     *            parameters map
     * @param properties
     *            properties map
     * @return reponsing string
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
