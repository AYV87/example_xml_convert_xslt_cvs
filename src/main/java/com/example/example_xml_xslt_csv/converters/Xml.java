package com.example.example_xml_xslt_csv.converters;

import com.example.example_xml_xslt_csv.db.Db;
import com.example.example_xml_xslt_csv.domain.RawEntry;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;

public class Xml {

    private Db db;

    public Xml(Db db) {
        this.db = db;
    }
    public void createXmlFile(String filename, String tmpFileName) throws Exception, IOException, XMLStreamException, TransformerException {
        List<RawEntry> dataFromDb = db.getDataFromDb(); // запрашиваем все данные из бд

        XMLOutputFactory factory = XMLOutputFactory.newInstance(); // создание фабрики для записи в xml файл
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileWriter(tmpFileName)); // создаем поток для записи

        writer.writeStartDocument("UTF8", "1.0"); // заголовок для xml
        writer.writeStartElement("articles"); // объявление начала перечисления article объектов
        // пробегаемся по всем данным из dataFromDb и превращаем их в xml записи
        for (RawEntry rawEntry : dataFromDb) {
            writer.writeStartElement("article");
            writer.writeAttribute("id_art", String.valueOf(rawEntry.getId_art()));
            writer.writeAttribute("name", rawEntry.getName());
            writer.writeAttribute("code", String.valueOf(rawEntry.getCode()));
            writer.writeAttribute("username", rawEntry.getUserName());
            writer.writeAttribute("guid", rawEntry.getGuid());
            writer.writeEndElement();
        }
        // пишем что закрываем элемент articles
        writer.writeEndElement();
        // пишем что конец документа
        writer.writeEndDocument();
        writer.close();

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        // преобразование выходящего файла в соответствии с transformer
        transformer.transform(new StreamSource(
                        new BufferedInputStream(new FileInputStream(tmpFileName))),
                new StreamResult(new FileOutputStream(filename))
        );
    }
}
