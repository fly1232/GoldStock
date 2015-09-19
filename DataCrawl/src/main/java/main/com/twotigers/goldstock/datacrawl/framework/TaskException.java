package main.com.twotigers.goldstock.datacrawl.framework;

/**
 * Created by Administrator on 2015/9/19.
 */
public class TaskException extends Exception {

    public TaskException(Throwable t){
        super(t);
    }

    public TaskException(String msg, Throwable t){
        super(msg, t);
    }
}
