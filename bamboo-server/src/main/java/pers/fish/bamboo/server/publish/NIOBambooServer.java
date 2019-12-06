package pers.fish.bamboo.server.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.fish.bamboo.server.publish.BambooServer;
import pers.fish.bamboo.server.publish.BIOBambooServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * nio服务发布
 *
 * @author fish
 * @date 2019/12/5 23:07
 */
public class NIOBambooServer implements BambooServer{

    private static final Logger logger = LoggerFactory.getLogger(BIOBambooServer.class);

    private Selector selector;

    //private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private int port;

    public NIOBambooServer(int port) {
        this.port = port;
    }

    @Override
    public void  publish(Object publish) throws Exception {
        ServerSocketChannel ss = null;
        try {
            ss = ServerSocketChannel.open();
            ss.bind(new InetSocketAddress(port));
            ss.configureBlocking(false);
            selector = Selector.open();
            ss.register(selector, SelectionKey.OP_ACCEPT);

            while (true){
                handleRequestData(ss);
            }
        }finally {
            if (ss != null) {
                ss.close();
            }
            if (selector != null) {
                selector.close();
            }
        }

    }

    /**
     * 处理请求数据
     * @param ss {@link ServerSocketChannel}
     */
    private void handleRequestData(ServerSocketChannel ss) throws IOException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()){
            SelectionKey key = iterator.next();
            iterator.remove();

            if(key.isReadable()){
                //key可读

            }
        }
    }


}
