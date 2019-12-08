package pers.fish.bamboo.client.proxy;

import pers.fish.bamboo.common.config.XMLConfiguration;

import java.lang.reflect.Proxy;

/**
 * bamboo客户端
 *
 * @author fish
 * @date 2019/12/1 11:39
 */
public class BIOBambooClient implements BambooClient{

    private XMLConfiguration xmlConfiguration = null;

    public BIOBambooClient() {
        xmlConfiguration = new XMLConfiguration();
    }

    @Override
    public <T> T getRPCObject(Class<T> clazz) {

        if(!clazz.isInterface()){
            throw new IllegalArgumentException("class must be interface");
        }
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RPCInvocationHandler(clazz,xmlConfiguration));

        return (T)obj;
    }
}
