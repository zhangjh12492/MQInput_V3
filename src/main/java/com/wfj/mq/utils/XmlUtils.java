package com.wfj.mq.utils;

import net.sf.json.JSONArray;
import net.sf.json.xml.XMLSerializer;

/**
 * Created by MaYong on 2014/9/1.
 */
public class XmlUtils {
    /**
     * bean to xml
     *
     * @param o
     * @return
     */
    public static String beanToXml(Object o) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setRootName("data");
        xmlSerializer.setElementName("bean");
        return xmlSerializer.write(JSONArray.fromObject(o));
    }
}
