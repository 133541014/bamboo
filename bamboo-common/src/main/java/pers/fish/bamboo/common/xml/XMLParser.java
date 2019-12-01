package pers.fish.bamboo.common.xml;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.fish.bamboo.common.annotation.XMLElement;

import java.lang.reflect.Field;
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
     * @param xmlStr
     */
    @SuppressWarnings("unchecked")
    public static Element getNodeByName(String xmlStr, String attribute, String value) {
        if (StringUtils.isEmpty(xmlStr) || StringUtils.isEmpty(attribute) || StringUtils.isEmpty(value)) {
            return null;
        }
        Element root = parseXmlToRoot(xmlStr);
        if (root == null) {
            return null;
        }
        return getNodeByName(root, attribute, value);
    }

    /**
     * @param attribute
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Element getNodeByName(Element root, String attribute, String value) {

        if (value.equals(root.attributeValue(attribute))) {
            return root;
        }

        List<Element> childrens = root.elements();

        for (Element children : childrens) {
            if (value.equals(children.attributeValue(attribute))) {
                return children;
            } else {
                Element result = getNodeByName(children, attribute, value);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * @param result
     * @return
     */
    public static Element parseXmlToRoot(String result) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(result);
            Element root = doc.getRootElement();
            return root;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static <T> T parseXmlToBean(T t, String xml) {
        Class<?> clazz = t.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(XMLElement.class)) {

                continue;
            }

            XMLElement xmlElement = field.getAnnotation(XMLElement.class);

            String attribute = xmlElement.attribute();
            String attributeValue = xmlElement.attributeValue();

            Element element = getNodeByName(xml, attribute, attributeValue);

            if (element == null) {
                return t;
            }

            //field.set(t,element.get);

        }

        return t;

    }
}
