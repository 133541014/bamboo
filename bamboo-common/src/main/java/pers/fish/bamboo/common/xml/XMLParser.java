package pers.fish.bamboo.common.xml;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * xml解析类
 *
 * @author fish
 * @date 2019/12/1 12:04
 */
public class XMLParser {

    private static final Logger logger = LoggerFactory.getLogger(XMLParser.class);

    /**
     * 获取xml文档对象
     *
     * @param inputStream 文件流
     * @return 文档对象
     */
    public static Document getDocument(InputStream inputStream) {

        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);
            return document;
        } catch (Exception e) {
            logger.error("read xml error", e);
        }
        return null;
    }

    public static <T> T convertXML2Object(InputStream inputStream,Class<T> clazz){
        try {
            JAXBContext context=JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller=context.createUnmarshaller();
            Object obj = unmarshaller.unmarshal(inputStream);
            return (T)obj;
        } catch (Exception e) {
            logger.error("convert xml to class "+clazz.getSimpleName()+" error", e);
        }
        return null;
    }

}
