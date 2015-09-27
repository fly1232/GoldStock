package main.com.twotigers.goldstock.datacrawl.utils;

import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/9/20.
 */
public class HttpDownloader {
    public static interface LineProc {

        public void proc(String line, boolean isFirstLine );
    }

    public void downloadTextFile(String fileUrl, String encoding, LineProc lineProc ) throws IOException {
        HttpURLConnection urlConnection = null;

        URL url = new URL(fileUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);

        InputStream in = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while (!reader.ready()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String lineStr;
        boolean isFirstLine = true;
        while ((lineStr = reader.readLine())!=null) {
            lineProc.proc(lineStr, isFirstLine);
            isFirstLine = false;
        }
        reader.close();
        in.close();
    }
}
