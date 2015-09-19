package test.main;

import junit.framework.TestCase;
import main.com.twotigers.goldstock.datacrawl.utils.DateUtity;
import main.com.twotigers.goldstock.datacrawl.utils.DbUtity;
import org.springframework.cglib.core.Local;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015/9/19.
 */
public class DateUtityTestCase extends TestCase {

    public void testParseCstToDate(){
        String err = null;
        try {
            String cstStr = "Wed Jul 01 00:00:01 CST 2015";
            Date dt = DateUtity.parseXueqiuFormatToDate(cstStr);
            System.out.println(DateUtity.dateToDateStr(dt));

        } catch (ParseException e) {
            err = e.getMessage();
            err = (null == err) ? "empty exception":err;
        }
        Assert.isNull(err, err);
    }
}
