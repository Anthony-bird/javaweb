package com.feng.server;



import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebDom4jTest { //用于解析xml
    private List<Entity> entityList; //用于储存实体类，每一个实体类一个servlet-name对应一个servlet-class
    private List<Mapping> mappingList;//用于储存映射类，每一个servlet-name对应一个url-pattern

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<Mapping> mappingList) {
        this.mappingList = mappingList;
    }

    //构造方法
    public WebDom4jTest() {
        entityList = new ArrayList<Entity>();
        mappingList = new ArrayList<Mapping>();
    }

    //获取Document对象的方法
    public Document getDocument(){

        try {
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //调用read方法
            return reader.read(new File("web/WEB-INF/web.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取元素
    public void parse(Document doc) {
        //1获取根元素
        Element root = doc.getRootElement(); //web-app
        //2获取servlet子元素
        for (Iterator<Element> ite = root.elementIterator("servlet"); ite.hasNext();) {
            Element subElement = ite.next(); //得到每一个servlet
            //创建一个实体类
            Entity ent = new Entity(); //用于储存servlet-name与servlet-class
            for (Iterator<Element> subIte = subElement.elementIterator(); subIte.hasNext();) {
                Element ele = subIte.next(); //servlet-name与servlet-class都有可能
                if ("servlet-name".equals(ele.getName())) {
                    ent.setName(ele.getText());
                } else if ("servlet-class".equals(ele.getName())) {
                    ent.setClazz(ele.getText());
                }
            }
            entityList.add(ent);//放到集合中
        }
        //测试
       /* for (Entity entity : entityList) {
            System.out.println(entity.getName() + "\t" + entity.getClazz());
        }*/

        //解析servlet-mapping

        for (Iterator<Element> ite = root.elementIterator("servlet-mapping"); ite.hasNext();){
            Element subEle = ite.next();//得到每一个servlet-mapping
            //创建一个mapping对象
            Mapping map = new Mapping();
            //解析mapping下面每一个子元素
            for (Iterator<Element> subIte = subEle.elementIterator(); subIte.hasNext();){
                Element ele = subIte.next();//可能是name，也可能是url
                if ("servlet-name".equals(ele.getName())) {
                    map.setName(ele.getText());
                } else if ("url-pattern".equals(ele.getName())) {
                    //获取集合对象，调用集合对象的添加方法，添加元素
                    map.getUrlPattern().add(ele.getText());
                }
            }
            mappingList.add(map);

        }
        //测试
        /*for (Mapping m : mappingList) {
            System.out.println(m.getName());
            for (String s : m.getUrlPattern()) {
                System.out.println(s);
            }
        }*/
    }

    //用于测试
    public static void main(String[] args) {
        WebDom4jTest web = new WebDom4jTest();
        web.parse(web.getDocument());
    }
}
