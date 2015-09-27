package main.com.twotigers.goldstock.datacrawl.common;

/**
 * Created by Administrator on 2015/9/15.
 */
public final class MongoDbConfig {

    /**
     * mongodb server ip
     */
    private String serverIp;

    /**
     * mongodb port
     */
    private int serverPort;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

}
