package org.example.utility;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This class is responsible for reading the XML template files.
 */
public class FileReaderUtils {

    /**
     * Reads the content of a file located in the classpath and returns it as a String.
     *
     * @param templatePath the path to the template file in the classpath
     * @return the content of the file as a String
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static String readFileAsString(String templatePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(templatePath);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}