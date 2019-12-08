package pers.fish.bamboo.client.proxy;

import pers.fish.bamboo.common.config.BambooConfig;
import pers.fish.bamboo.common.config.XMLConfiguration;
import pers.fish.bamboo.common.model.RPCRequest;
import pers.fish.bamboo.common.model.RPCResponse;
import pers.fish.bamboo.common.util.ByteUtils;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

/**
 * 动态代理处理类
 *
 * @author fish
 * @date 2019/12/1 11:24
 */
public class RPCInvocationHandler implements InvocationHandler {

    private String implName = null;
    private XMLConfiguration xmlConfiguration = null;

    public <T> RPCInvocationHandler(Class<T> clazz, XMLConfiguration xmlConfiguration) {
        this.xmlConfiguration = xmlConfiguration;
        String publishName = clazz.getName();
        BambooConfig bambooConfig = xmlConfiguration.getBambooConfig();
        List<BambooConfig.Publish> publishList = bambooConfig.getPublishList();
        for (BambooConfig.Publish publish : publishList) {
            if (publishName.equals(publish.getName())) {
                implName = publish.getImplement();
            }
        }

        if (implName == null) {
            throw new RuntimeException(clazz.getSimpleName() + " do not have a implement in config");
        }


    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = doRPCTransfer(method, args);
        return result;
    }

    private Object doRPCTransfer(Method method, Object[] args) {
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            RPCRequest request = new RPCRequest();
            request.setClassName(implName);
            request.setMethodName(method.getName());

            if (args != null && args.length > 0) {
                request.setParameters(args);
            }

            BambooConfig bambooConfig = xmlConfiguration.getBambooConfig();
            BambooConfig.Server server = bambooConfig.getServer();
            socket = new Socket(server.getHost(), server.getPort());
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(ByteUtils.getBytes(request));
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RPCResponse rpcResponse = (RPCResponse) objectInputStream.readObject();
            Object result = rpcResponse.getResult();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (objectOutputStream != null) objectOutputStream.close();

                if (objectInputStream != null) objectInputStream.close();

                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
