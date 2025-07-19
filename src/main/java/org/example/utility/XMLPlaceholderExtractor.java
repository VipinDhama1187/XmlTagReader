package org.example.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class XMLPlaceholderExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLPlaceholderExtractor.class);

    private static final Map<String, FieldProcessor> PROCESSORS = new HashMap<>();

    static {
        PROCESSORS.put("TxId", FieldProcessors.TX_ID_PROCESSOR);
        PROCESSORS.put("EndToEndId", FieldProcessors.E2E_ID_PROCESSOR);
        PROCESSORS.put("OrgnlTxId", FieldProcessors.ORGNL_TX_ID_PROCESSOR);
        PROCESSORS.put("OrgnlEndToEndId", FieldProcessors.ORGNL_E2E_ID_PROCESSOR);
        PROCESSORS.put("AccptncDtTm", FieldProcessors.STS_DATE_TIME_PROCESSOR);
        PROCESSORS.put("IntrBkSttlmAmt", FieldProcessors.AMOUNT_PROCESSOR);
        PROCESSORS.put("OrgnlIntrBkSttlmAmt", FieldProcessors.AMOUNT_PROCESSOR);
        PROCESSORS.put("BICFI", FieldProcessors.BICFI_PROCESSOR);
        PROCESSORS.put("IBAN", FieldProcessors.IBAN_PROCESSOR);
    }

    public static Map<String, String> extractValues(String xmlContent) throws Exception {
        Map<String, String> values = new HashMap<>();
        Deque<String> path = new ArrayDeque<>();

        XMLStreamReader reader = XMLInputFactory.newInstance()
                .createXMLStreamReader(new StringReader(xmlContent));

        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLStreamConstants.START_ELEMENT) {
                String localName = reader.getLocalName();
                path.push(localName);

                FieldProcessor processor = PROCESSORS.get(localName);
                if (processor != null) {
                    processor.process(reader, values, path);
                }

            } else if (event == XMLStreamConstants.END_ELEMENT) {
                path.pop();
            }
        }

        reader.close();
        logExtractedValues(values);
        return values;
    }
    public static void logExtractedValues(Map<String, String> values) {
        LOGGER.debug("==== Extracted Placeholder Values ====");
        values.forEach((key, value) -> LOGGER.debug("{} => {}", key, value));
        LOGGER.debug("======================================");
    }

}
