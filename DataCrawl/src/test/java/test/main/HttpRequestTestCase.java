//package test.main;
//
//import junit.framework.TestCase;
//import main.com.twotigers.goldstock.datacrawl.connect.XueqiuConnector;
//import main.com.twotigers.goldstock.datacrawl.utils.HttpRequester;
//import org.springframework.util.Assert;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2015/9/19.
// */
//public class HttpRequestTestCase extends TestCase {
//
//    public void testGet(){
//        String errMsg = null;
//        try {
//            //String urlStr = "http://xueqiu.com/hq#exchange=CN&plate=1_1_1&firstName=1&secondName=1_1&type=sza";
//            String urlStr = "http://xueqiu.com/stock/quote_order.json";
////            URL url = new URL(URLEncoder.encode(urlStr, "UTF-8"));
//            Map<String,String> paramaters = new HashMap<String, String>();
//            paramaters.put("page", "1");
//            paramaters.put("size", "30");
//            paramaters.put("order", "desc");
//            paramaters.put("exchange","CN");
//            paramaters.put("stockType","sza");
//            paramaters.put("column", URLEncoder.encode("symbol,name","UTF-8"));
//            paramaters.put("orderBy", "percent");
//            //paramaters.put("_", "1442599832132");
//
//            Map<String,String> properties = new HashMap<String, String>();
////            properties.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
////            properties.put("connection", "Keep-Alive");
////            properties.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36");
//            properties.put("Cookie", XueqiuConnector.STR_COOKIE_XUEQIU);
//
////
////            urlConnection.setRequestProperty("accept", "");
////            urlConnection.setRequestProperty("connection", "Keep-Alive");
////            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36");
////
//            HttpRequester httpRequester = new HttpRequester();
//            httpRequester.setDefaultContentEncoding("utf-8");
//            String jsonStr = httpRequester.sendGet(urlStr, paramaters,properties);
//
//            System.out.println(" ============================= ");
//
//            System.out.println(" Contents of get request ");
//
//            System.out.println(" ============================= ");
//
//            System.out.println(jsonStr);
//
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
