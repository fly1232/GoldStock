//package main.com.twotigers.goldstock.datacrawl.task.stockcode;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * Created by Administrator on 2015/9/16.
// * 股票基本信息
// */
//public class CrawlStockInfo implements Serializable{
//    public static class StockInfo implements Serializable {
//        /**
//         * 股票代码
//         */
//        private String stockCode;
//
//        /**
//         * 股票名称
//         */
//        private String stockName;
//
//        public String getStockCode() {
//            return stockCode;
//        }
//
//        public void setStockCode(String stockCode) {
//            this.stockCode = stockCode;
//        }
//
//        public String getStockName() {
//            return stockName;
//        }
//
//        public void setStockName(String stockName) {
//            this.stockName = stockName;
//        }
//    }
//
//    private String column;
//
//    private List<StockInfo> data;
//
//    private Double count;
//
//    public String getColumn() {
//        return column;
//    }
//
//    public void setColumn(String column) {
//        this.column = column;
//    }
//
//    public List<StockInfo> getData() {
//        return data;
//    }
//
//    public void setData(List<StockInfo> data) {
//        this.data = data;
//    }
//
//    public Double getCount() {
//        return count;
//    }
//
//    public void setCount(Double count) {
//        this.count = count;
//    }
//
//}
