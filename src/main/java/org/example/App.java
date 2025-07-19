package org.example;

import org.apache.commons.text.StringSubstitutor;
import org.example.utility.FileReaderUtils;
import org.example.utility.XMLPlaceholderExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOGGER  = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        try {
            String xml = FileReaderUtils.readFileAsString("pacs4.xml");
            LOGGER.debug("Pacs4 xml : [{}]", xml);
            Map<String, String> result = XMLPlaceholderExtractor.extractValues(xml);

            String template = FileReaderUtils.readFileAsString("pacs2_from_pacs4.xml");
            String pacs2 =  StringSubstitutor.replace(template, result, "${", "}");

            LOGGER.debug("Pacs2 xml after replacement : [{}]", pacs2);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
