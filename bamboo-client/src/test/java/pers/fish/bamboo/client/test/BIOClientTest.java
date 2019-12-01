package pers.fish.bamboo.client.test;

import org.junit.Ignore;
import org.junit.Test;
import pers.fish.bamboo.client.proxy.BIOBambooClient;
import pers.fish.bamboo.common.config.XMLConfiguration;
import pers.fish.bamboo.test.api.IUserService;
import pers.fish.bamboo.test.api.User;

/**
 * BIOServer测试
 *
 * @author fish
 * @date 2019/12/1 12:41
 */
@Ignore
public class BIOClientTest {

    @Test
    public void start(){

        BIOBambooClient bioBambooClient = new BIOBambooClient();
        IUserService iUserService = bioBambooClient.getRPCObject(IUserService.class);
        User user = iUserService.getUser(1);
        System.out.println(user);
    }
}
