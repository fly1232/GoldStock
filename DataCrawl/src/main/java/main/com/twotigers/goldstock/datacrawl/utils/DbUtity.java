package main.com.twotigers.goldstock.datacrawl.utils;

import com.mongodb.*;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import main.com.twotigers.goldstock.datacrawl.common.Constants;
import main.com.twotigers.goldstock.datacrawl.common.MongoDbConfig;
import main.com.twotigers.goldstock.datacrawl.common.MongoDbConfigTool;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class DbUtity {

    public static MongoClient createMongoClient() throws UnknownHostException {
        MongoDbConfig mongoDbConfig = MongoDbConfigTool.getMongoDbConfig();
        MongoClient mongoClient = new MongoClient(mongoDbConfig.getServerIp(), mongoDbConfig.getServerPort());
        return mongoClient;
    }

    /**
     * 得到一个数据集的记录条数
     * @param mongoClient
     * @param tableName
     * @return
     */
    public static long getRowCountOfTable(MongoClient mongoClient, String tableName) {
        DB db = mongoClient.getDB(Constants.DATABASE_NAME);
        if (db != null){
            DBCollection table = db.getCollection(tableName);
            if (table!=null){
                return table.count();
            }
        }
        return 0L;
    }

    /**
     * 得到一个数据集的记录条数
     * @param tableName
     * @return
     */
    public static long getRowCountOfTable(MongoClient mongoClient, String tableName, DBObject queryObj) throws UnknownHostException {
        DB db = mongoClient.getDB(Constants.DATABASE_NAME);
        if (db != null){
            DBCollection table = db.getCollection(tableName);
            if (table!=null){
                return table.find(queryObj).count();
            }
        }
        return 0L;
    }

    /**
     * 为某个数据集建立索引
     * @param tableName
     * @return
     */
    public static void createIndexForTable(MongoClient mongoClient, String tableName, DBObject indexObj) {
        DB db = mongoClient.getDB(Constants.DATABASE_NAME);
        if (db != null){
            DBCollection table = db.getCollection(tableName);
            if (table!=null){
                table.createIndex(indexObj);
            }
        }
    }

    /**
     * 为某个数据集建立索引
     * @param tableName
     * @return
     */
    public static void insertDbObjects(MongoClient mongoClient, String tableName, List<DBObject> dbObjects) {
        if ((dbObjects==null)||dbObjects.isEmpty()) return;
        DB db = mongoClient.getDB(Constants.DATABASE_NAME);
        if (db != null){
            DBCollection table = db.getCollection(tableName);
            if (table!=null){
                table.insert(dbObjects);
            }
        }
    }

    /**
     * 得到某个数据集某个字段的列表
     * @param mongoClient
     * @param tableName
     * @return
     */
    public static <T> List<T> getFieldValuesOfTable(MongoClient mongoClient, String tableName, String fieldName) {
        DB db = mongoClient.getDB(Constants.DATABASE_NAME);
        if (db != null){
            DBCollection table = db.getCollection(tableName);
            if (table!=null){
                List<T> list = new ArrayList<>();
                DBCursor dbCursor = table.find();
                while (dbCursor.hasNext()){
                    list.add((T)dbCursor.next().get(fieldName));
                }
                return list;
            }
        }
        return null;
    }

    /**
     * 得到某个数据集某个字段的最大（小）值
     * @param mongoClient
     * @param tableName
     * @return
     */
    public static <T> T getMaxMinFieldValuesOfTable(MongoClient mongoClient, String tableName, DBObject queryObj, String fieldName, boolean isMax) {
        DB db = mongoClient.getDB(Constants.DATABASE_NAME);
        if (db != null){
            DBCollection table = db.getCollection(tableName);
            if (table!=null){
                List<T> list = new ArrayList<>();
                DBCursor dbCursor = null;
                try {
                    dbCursor = table.find(queryObj)
                            .sort(new BasicDBObject(fieldName, isMax ? -1 : 1));
                    if (dbCursor.hasNext()) {
                        return (T) dbCursor.next().get(fieldName);
                    }
                }
                finally {
                    if (dbCursor != null) dbCursor.close();
                }
            }
        }
        return null;
    }

}
