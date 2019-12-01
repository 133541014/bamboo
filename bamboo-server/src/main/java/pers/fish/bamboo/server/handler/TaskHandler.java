package pers.fish.bamboo.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.fish.bamboo.common.model.RPCRequest;
import pers.fish.bamboo.common.model.RPCResponse;
import pers.fish.bamboo.common.xml.XMLParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * socket任务处理
 *
 * @author fish
 * @date 2019/11/30 18:05
 */
public class TaskHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TaskHandler.class);

    private Socket socket = null;
    private Object publish = null;

    public TaskHandler(Socket socket, Object publish) {
        this.socket = socket;
        this.publish = publish;
    }

    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            InputStream inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            RPCRequest request = (RPCRequest) objectInputStream.readObject();
            Object result = handle(request, publish);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            RPCResponse rpcResponse = new RPCResponse();
            rpcResponse.setResult(result);
            objectOutputStream.writeObject(rpcResponse);
            objectOutputStream.flush();
        } catch (Exception e) {

            logger.error("处理本地调用方法失败", e);

        } finally {
            try {
                if (socket != null) socket.close();

                if (objectOutputStream != null) objectOutputStream.close();

                if (objectInputStream != null) objectInputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private Object handle(RPCRequest rpcRequest, Object publish) throws Exception {
        Class<?> clazz = publish.getClass();
        Object[] parameters = rpcRequest.getParameters();
        Class[] types = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = parameters[i].getClass();
            types[i] = parameterType;


        }
        Method method = clazz.getMethod(rpcRequest.getMethodName(), types);
        Object result = method.invoke(publish, parameters);
        return result;
    }


}
