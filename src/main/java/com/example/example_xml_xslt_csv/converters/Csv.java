package com.example.example_xml_xslt_csv.converters;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

public class Csv {

    public void doTransformXlsToCsv(String xmlFilePath, String csvFilePath, String xslTrmplatePath) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // создаем фабрику для создания документа
        DocumentBuilder builder = factory.newDocumentBuilder(); // создай мне builder
        Document document = builder.parse(xmlFilePath); // считай xml файлик откуда будем брать данные
        StreamSource stylesource = new StreamSource(xslTrmplatePath); // считай файл с шаблоном csv
        Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource); // создаем преобразователя
        Source source = new DOMSource(document); // исходный xml файлик
        Result outputTarget = new StreamResult(new File(csvFilePath)); // резултатт преобразования
        transformer.transform(source, outputTarget); // вызываем само преобразование и запись в файл
    }
}
