package pers.fish.bamboo.server.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.fish.bamboo.common.model.RPCRequest;
import pers.fish.bamboo.common.model.RPCResponse;
import pers.fish.bamboo.common.util.ByteUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio服务发布
 *
 * @author fish
 * @date 2019/12/5 23:07
 */
public class NIOBambooServer implements BambooServer {

    private static final Logger logger = LoggerFactory.getLogger(BIOBambooServer.class);

    private Selector selector;

    private int port;

    public NIOBambooServer(int port) {
        this.port = port;
    }

    @Override
    public void publish(Object publish) throws Exception {
        ServerSocketChannel ss = null;
        try {
            ss = ServerSocketChannel.open();
            ss.bind(new InetSocketAddress(port));
            ss.configureBlocking(false);
            selector = Selector.open();
            ss.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int total = selector.select();
                if (total != 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        process(key, publish);

                    }
                }
            }
        } finally {
            if (ss != null) {
                ss.close();
            }
            if (selector != null) {
                selector.close();
            }
        }

    }

    private void process(SelectionKey key, Object publish) throws Exception {
        try {
            if (key.isAcceptable()) {
                ServerSocketChannel server = (ServerSocketChannel) key.channel();
                //这个方法体现非阻塞，不管你数据有没有准备好
                //你给我一个状态和反馈
                SocketChannel channel = server.accept();
                //一定一定要记得设置为非阻塞
                channel.configureBlocking(false);
                //当数据准备就绪的时候，将状态改为可读
                key = channel.register(selector, SelectionKey.OP_READ);
            } else if (key.isReadable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (channel.read(buffer) > 0) {
                    //将channel改为读取状态
                    buffer.flip();
                    byteArrayOutputStream.write(buffer.array());
                    buffer.clear();
                }
                RPCRequest request = (RPCRequest) ByteUtils.getObject(byteArrayOutputStream.toByteArray());
                Object result = handle(request, publish);
                key = channel.register(selector, SelectionKey.OP_WRITE);
                key.attach(result);
            } else if (key.isWritable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                Object result = key.attachment();
                RPCResponse rpcResponse = new RPCResponse();
                rpcResponse.setResult(result);
                channel.write(ByteUtils.getByteBuffer(rpcResponse));
                channel.close();
            }
        } catch (Exception e) {
            logger.error("nio 处理任务异常", e);
        }

    }

    private Object handle(RPCRequest rpcRequest, Object publish) throws Exception {
        Class<?> clazz = publish.getClass();
        Object[] parameters = rpcRequest.getParameters();
        Method method = null;

        if (parameters != null) {
            Class[] types = new Class[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Class<?> parameterType = parameters[i].getClass();
                types[i] = parameterType;


            }
            method = clazz.getMethod(rpcRequest.getMethodName(), types);
        } else {
            method = clazz.getMethod(rpcRequest.getMethodName());
        }

        Object result = method.invoke(publish, parameters);
        return result;
    }


}
