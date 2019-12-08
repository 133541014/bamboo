package pers.fish.bamboo.client.proxy;

/**
 * 写点什么吧
 *
 * @author fish
 * @date 2019/12/8 13:14
 */
public interface BambooClient {

    public <T> T getRPCObject(Class<T> clazz);
}
