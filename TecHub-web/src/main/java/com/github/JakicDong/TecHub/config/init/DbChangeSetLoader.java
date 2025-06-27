package com.github.JakicDong.TecHub.config.init;


import org.springframework.core.io.ClassPathResource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JakicDong
 * @time 2025.6.27
 * @description 数据库的变更加载器(在liquibase不启动的时候会用到)
 * @version 1.0.0
 */
public class DbChangeSetLoader {
    /**
     * 获取xmlReader
     *
     * @return
     * @throws Exception
     */
    public static XMLReader getInstance() throws Exception {
        // javax.xml.parsers.SAXParserFactory 原生api获取factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // javax.xml.parsers.SAXParser 原生api获取parse
        SAXParser saxParser = factory.newSAXParser();
        // 获取xml
        return saxParser.getXMLReader();
    }


    /**
     * 加载Liquibase变更集资源文件
     * @param source 变更集文件路径（格式如："classpath:db/changelog/db.changelog-master.xml"）
     * @return 返回所有需要执行的SQL脚本资源列表
     */
    public static List<ClassPathResource> loadDbChangeSetResources(String source) {
        try {
            // 1. 获取SAX解析器实例
            XMLReader xmlReader = getInstance();

            // 2. 第一阶段解析：处理主变更日志文件（查找include标签）
            ChangeHandler logHandler = new ChangeHandler("include", "file");
            xmlReader.setContentHandler(logHandler);
            // 解析主变更文件（去除classpath:前缀并去除前后空格）
            xmlReader.parse(new ClassPathResource(source.replace("classpath:", "").trim()).getFile().getPath());
            // 获取所有被include的子变更集文件路径
            List<String> changeSetFiles = logHandler.getSets();

            // 3. 第二阶段解析：处理每个子变更集文件（查找sqlFile标签）
            List<ClassPathResource> result = new ArrayList<>();
            ChangeHandler setHandler = new ChangeHandler("sqlFile", "path");
            for (String set : changeSetFiles) {
                xmlReader.setContentHandler(setHandler);
                // 解析每个子变更集文件
                xmlReader.parse(new ClassPathResource(set).getFile().getPath());
                // 将找到的所有SQL文件路径转为ClassPathResource并添加到结果集
                result.addAll(setHandler.getSets().stream()
                        .map(ClassPathResource::new)
                        .collect(Collectors.toList()));
                // 重置处理器以复用
                setHandler.reset();
            }
            return result;
        } catch (Exception e) {
            // 统一抛出运行时异常
            throw new IllegalStateException("加载初始化脚本异常!", e);
        }
    }


    public static class ChangeHandler extends DefaultHandler {
        private List<String> sets = new ArrayList<>();

        private final String tag;
        private final String attr;

        public ChangeHandler(String tag, String attr) {
            this.tag = tag;
            this.attr = attr;
        }

        /**
         * 解析xml
         *
         * @param uri
         * @param localName
         * @param qName
         * @param attributes
         * @throws SAXException
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (tag.equals(qName)) {
                sets.add(attributes.getValue(attr));
            }
        }

        public List<String> getSets() {
            return sets;
        }

        public void reset() {
            sets.clear();
        }
    }

}
