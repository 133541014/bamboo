package pers.fish.bamboo.client.test;

import com.test.ITestService;
import org.junit.Ignore;
import org.junit.Test;
import pers.fish.bamboo.client.proxy.BIOBambooClient;
import pers.fish.bamboo.client.proxy.BambooClient;
import pers.fish.bamboo.test.api.IUserService;
import pers.fish.bamboo.test.api.User;

/**
 * BIOServer测试
 *
 * @author fish
 * @date 2019/12/1 12:41
 */
@Ignore
public class ClientTest {

    @Test
    public void start() {

        BambooClient bambooClient = new BIOBambooClient();
        IUserService userService = bambooClient.getRPCObject(IUserService.class);
        User user = userService.getUser(1);
        System.out.println("接收到方法返回值: " + user);
    }
}
