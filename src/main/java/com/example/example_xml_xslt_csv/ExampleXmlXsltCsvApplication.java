package com.example.example_xml_xslt_csv;

import com.example.example_xml_xslt_csv.converters.Csv;
import com.example.example_xml_xslt_csv.converters.Xml;
import com.example.example_xml_xslt_csv.converters.Xslt;
import com.example.example_xml_xslt_csv.db.Db;
import com.example.example_xml_xslt_csv.location.DbException;
import com.example.example_xml_xslt_csv.location.DbSourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@SpringBootApplication
public class ExampleXmlXsltCsvApplication {

    private final static int MAX_COUNT_OF_ENTRIES = 105000;
    private final static String RESULT_XML_FILE_LOCATION = "data\\result.xml";
    private final static String XML_FILE_PATH = "data\\1.xml";
    private final static String XSLT_FILE_PATH = "data\\xmlTransformation.xslt";
    private final static String TMP_FILE_PATH = "data\\tmp.xml";
    private final static String CSV_TEMPLATE_FILE_PATH = "data\\csvTransformation.xsl";
    private final static String RESULT_CSV_FILE_PATH = "data\\result.csv";
    private static final Logger LOGGER  = LoggerFactory.getLogger(ExampleXmlXsltCsvApplication.class);


    public static void main(String[] args) {
        doCreateXmlFile(); // 1 задание
        doTransformXmlFileWithUseXslt(); // 2 задание
        doTransformXml2CsvWithCsvTemplate(); // 3 задание
    }

    private static void doCreateXmlFile() {
        DbSourse dbDataSource = new DbSourse();
        Db dao = new Db (dbDataSource);
        dao.doCreateAndFillDb(MAX_COUNT_OF_ENTRIES); // заполнение бд данными
        Xml xmlCreator = new Xml(dao);
        try {
            xmlCreator.createXmlFile(XML_FILE_PATH, TMP_FILE_PATH);
            dbDataSource.closeConnection();
            LOGGER.debug("1 сообщение отправлено!");
        } catch (DbException e) {
            LOGGER.error("Db error!", e);
        } catch (IOException e) {
            LOGGER.error("IO error!", e);
        } catch (XMLStreamException e) {
            LOGGER.error("XML error!", e);
        } catch (TransformerException e) {
            LOGGER.error("Transformer error!", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void doTransformXmlFileWithUseXslt() {
        Xslt transformator = new Xslt();
        try {
            // запускаем трансформацию данных
            transformator.doTransformXmlFile(XML_FILE_PATH,
                    XSLT_FILE_PATH,
                    RESULT_XML_FILE_LOCATION);
            LOGGER.debug("2 сообщение отправлено!");
        } catch (TransformerException e) {
            LOGGER.error("XsltTransformator error!", e);
        }
    }

        private static void doTransformXml2CsvWithCsvTemplate() {
            Csv csvCreator = new Csv();
            try {
                csvCreator.doTransformXlsToCsv(
                        RESULT_XML_FILE_LOCATION,
                        RESULT_CSV_FILE_PATH,
                        CSV_TEMPLATE_FILE_PATH
                );
                LOGGER.debug("3 сообщение отправлено!");
            } catch (TransformerException e) {
                LOGGER.error("CSV Transformer error!", e);
            } catch (IOException e) {
                LOGGER.error("CSV IO error!", e);
            } catch (SAXException e) {
                LOGGER.error("CSV SAX error!", e);
            } catch (ParserConfigurationException e) {
                LOGGER.error("CSV Parser error!", e);
            }
        }
    }




