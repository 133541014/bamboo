package pers.fish.bamboo.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.fish.bamboo.common.model.RPCRequest;
import pers.fish.bamboo.common.xml.XMLParser;
import pers.fish.bamboo.server.handler.TaskHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * bio 服务发布
 *
 * @author WangYichao
 * @date 2019/11/30 17:45
 */
public class BIOBambooServer {
    private static final Logger logger = LoggerFactory.getLogger(BIOBambooServer.class);

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private int port;

    public BIOBambooServer(int port) {
        this.port = port;
    }

    public void publish(Object publish) throws Exception {
        logger.debug("日志好用");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                threadPool.execute(new TaskHandler(socket,publish));
            }
        }finally {
            if(serverSocket != null){
                serverSocket.close();
            }
        }

    }
}
