package pers.fish.bamboo.client.test;

import com.test.ITestService;
import org.junit.Ignore;
import org.junit.Test;
import pers.fish.bamboo.client.proxy.BIOBambooClient;

/**
 * BIOServer测试
 *
 * @author fish
 * @date 2019/12/1 12:41
 */
@Ignore
public class BIOClientTest {

    @Test
    public void start() {

        BIOBambooClient bioBambooClient = new BIOBambooClient();
        ITestService testService = bioBambooClient.getRPCObject(ITestService.class);
        String result = testService.hello("100");
        System.out.println("接收到方法返回值: " + result);
    }
}
