package pers.fish.bamboo.client.proxy;

import java.lang.reflect.Proxy;

/**
 * –¥µ„ ≤√¥∞…
 *
 * @author fish
 * @date 2019/12/1 11:39
 */
public class BambooClient {

    public <T> T getRPCObject(Class<T> clazz) {

        if(!clazz.isInterface()){
            throw new IllegalArgumentException("class must be interface");
        }
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RPCInvocationHandler());

        return (T)obj;
    }
}
