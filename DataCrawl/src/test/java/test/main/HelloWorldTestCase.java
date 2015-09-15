package test.main;

import junit.framework.Assert;
import junit.framework.TestCase;
import main.HelloWorld;

/**
 * Created by xuyufei on 2015/9/12.
 */
public class HelloWorldTestCase extends TestCase {

    public void testHello(){
        String helloStr = HelloWorld.hello("tom");
        Assert.assertEquals(helloStr, "hello tom");
    }
}