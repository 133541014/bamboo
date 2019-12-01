package pers.fish.bamboo.common.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置文件对应配置类
 *
 * @author fish
 * @date 2019/12/1 12:30
 */
@XmlRootElement(name = "configuration")
public class BambooConfig {

    public static final String CONFIG_NAME = "bamboo.xml";

    public static class Server {

        private String host;

        private int port;

        @XmlAttribute(name = "host")
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        @XmlAttribute(name = "port")
        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class Publish {

        private String name;

        private String implement;

        @XmlAttribute(name = "name")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlAttribute(name = "implement")
        public String getImplement() {
            return implement;
        }

        public void setImplement(String implement) {
            this.implement = implement;
        }
    }

    private Server server = new Server();

    private List<Publish> publishList = new ArrayList<Publish>();

    @XmlElement(name = "server")
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @XmlElement(name = "publish")
    public List<Publish> getPublishList() {
        return publishList;
    }

    public void setPublishList(List<Publish> publishList) {
        this.publishList = publishList;
    }
}
