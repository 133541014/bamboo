package pers.fish.bamboo.server.test;

import org.junit.Ignore;
import org.junit.Test;
import pers.fish.bamboo.server.publish.BIOBambooServer;
import pers.fish.bamboo.server.publish.BambooServer;
import pers.fish.bamboo.server.publish.NIOBambooServer;
import pers.fish.bamboo.test.api.UserServiceImpl;

/**
 * BIOServer测试
 *
 * @author fish
 * @date 2019/12/1 12:41
 */
@Ignore
public class ServerTest {

    @Test
    public void start() throws Exception {

        BambooServer bambooServer = new NIOBambooServer(7799);
        bambooServer.publish(new UserServiceImpl());
    }
}
