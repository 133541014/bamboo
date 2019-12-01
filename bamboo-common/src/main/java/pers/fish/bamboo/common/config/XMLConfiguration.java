package pers.fish.bamboo.common.config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.fish.bamboo.common.xml.XMLParser;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XML配置操作类
 *
 * @author fish
 * @date 2019/12/1 12:31
 */
public class XMLConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(XMLConfiguration.class);

    private BambooConfig bambooConfig = null;

    public BambooConfig getBambooConfig() {
        return bambooConfig;
    }

    public XMLConfiguration() {

        init();
    }

    private void init() {

        InputStream inputStream = this.getClass().getResourceAsStream("/" + BambooConfig.CONFIG_NAME);

        if (inputStream == null) {
            throw new RuntimeException("could not found config file bamboo.xml in classpath root");
        }

        BambooConfig bambooConfig = XMLParser.convertXML2Object(inputStream, BambooConfig.class);

        if (bambooConfig == null) {
            throw new RuntimeException(BambooConfig.CONFIG_NAME + " content layout is error");
        }

        //校验节点
        Map<String, Integer> validateMap = new HashMap<String, Integer>();
        List<BambooConfig.Publish> publishList = bambooConfig.getPublishList();
        for (BambooConfig.Publish publish : publishList) {
            Integer total = validateMap.get(publish.getName());
            if (total != null) {
                throw new RuntimeException("element <publish> can not have two same name");
            } else {
                validateMap.put(publish.getName(), 1);
            }
        }

        this.bambooConfig = bambooConfig;
    }
}
