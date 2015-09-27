package main.com.twotigers.goldstock.datacrawl.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2015/9/15.
 * MongodbConfig reader tool
 */
public class MongoDbConfigTool {

    private final static Log logger = LogFactory.getLog(MongoDbConfigTool.class);

    private static MongoDbConfig mongoDbConfig = null;

    public static void initMongoDbConfig() throws IOException {
        if (mongoDbConfig == null) {
            Properties props = new Properties();
            loadProperties(props);
            mongoDbConfig = new MongoDbConfig();
            mongoDbConfig.setServerIp(props.getProperty("mongodb.ip").trim());
            mongoDbConfig.setServerPort(Integer.parseInt(props.getProperty("mongodb.port").trim()));
        }
    }

    public static MongoDbConfig getMongoDbConfig() {
        return mongoDbConfig;
    }

    private static void loadProperties(Properties props) throws IOException {
        InputStream in = MongoDbConfigTool.class.getResourceAsStream("/db.properties");
        try {
            props.load(in);
        } finally {
            in.close();
        }
    }

}
