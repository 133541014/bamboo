package pers.fish.bamboo.common.annotation;

import java.lang.annotation.*;

/**
 * 写点什么吧
 *
 * @author fish
 * @date 2019/12/1 12:19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XMLElement {

    /**
     * 属性名
     * @return 属性名
     */
    String attribute();

    /**
     * 属性值
     * @return 属性值
     */
    String attributeValue();
}
