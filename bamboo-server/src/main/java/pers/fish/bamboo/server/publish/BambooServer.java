package pers.fish.bamboo.server.publish;

/**
 * 写点什么吧
 *
 * @author fish
 * @date 2019/12/5 23:09
 */
public interface BambooServer {

    /**
     * 发布服务对象
     * @param publish 发布对象
     * @throws Exception 异常
     */
    public void  publish(Object publish) throws Exception;
}
