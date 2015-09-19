//package test.main;
//
//import junit.framework.TestCase;
//import org.springframework.util.Assert;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * Created by Administrator on 2015/9/19.
// */
//public class HttpGetTestCase2 extends TestCase {
//
//    public void testGet(){
//        String errMsg = null;
//        try {
//            //String urlStr = "http://xueqiu.com/hq#exchange=CN&plate=1_1_1&firstName=1&secondName=1_1&type=sza";
//            String urlStr = "http://xueqiu.com/stock/quote_order.json?page=1&size=90&order=desc&exchange=CN&stockType=sza&column=symbol&orderBy=percent";
////            URL url = new URL(URLEncoder.encode(urlStr, "UTF-8"));
//            URL url = new URL(urlStr);
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            //urlConnection.setRequestProperty();
//            urlConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//            urlConnection.setRequestProperty("connection", "Keep-Alive");
//            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36");
//            urlConnection.setRequestProperty("Cookie", "bid=9b15a2aa90149bcac4ad19830e783e9d_i77n2wc5; s=99d1vonc3l; xq_a_token=57b3a250eeedbb43ab231666cdb8fa1aab3a7bea; xq_r_token=244e4f2f9d234a41f9e7a5a34b582ba90d8dc970; u=4330033544; xq_token_expire=Sun%20Oct%2011%202015%2023%3A01%3A03%20GMT%2B0800%20(CST); xq_is_login=1; __utma=1.1649449937.1427454658.1442507370.1442592091.158; __utmc=1; __utmz=1.1442115445.152.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; Hm_lvt_1db88642e346389874251b5a1eded6e3=1442334616,1442412275,1442507370,1442592091; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1442592141");
////            urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//            urlConnection.connect();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//
//            System.out.println(" ============================= ");
//
//            System.out.println(" Contents of get request ");
//
//            System.out.println(" ============================= ");
//
//            String lines;
//
//            while ((lines = reader.readLine()) != null) {
//
//                System.out.println(lines);
//
//            }
//
//            reader.close();
//
//            // 断开连接
//
//            urlConnection.disconnect();
//
//            System.out.println(" ============================= ");
//
//            System.out.println(" Contents of get request ends ");
//
//            System.out.println(" ============================= ");
//        }
//        catch (Exception ex){
//            errMsg = ex.toString();
////            System.out.println(ex);
//        }
//        Assert.isNull(errMsg, "发生错误:"+errMsg);
//    }
//}
