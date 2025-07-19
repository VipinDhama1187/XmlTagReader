package org.example;

import org.example.utility.FileReaderUtils;
import org.example.utility.XMLPlaceholderExtractor;

import java.io.IOException;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            String xml = FileReaderUtils.readFileAsString("pacs4.xml");
            System.out.println(xml);
            Map<String, String> result = XMLPlaceholderExtractor.extractValues(xml);
            result.forEach((k, v) -> System.out.println(k + " => " + v));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
