package com.example.example_xml_xslt_csv.converters;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class Xslt {

    public void doTransformXmlFile(String sourceFilePath, String xsltFilePath, String outFilePath) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance(); // создаем фабрику трансформер
        Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFilePath))); // считываем шаблон преобразования
        StreamSource source = new StreamSource(new File(sourceFilePath)); // исходный файл, в нашем случае 1.xml
        StreamResult result = new StreamResult(new File(outFilePath)); // результриующий файл, куда будет записан результат трансформации
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        // здесь происходит само преобразование
        transformer.transform(source, result);

    }
}