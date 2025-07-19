package org.example.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class FieldProcessors {

    public static final FieldProcessor ORGNL_TX_ID_PROCESSOR = (reader, values, path) ->
            values.put("OrgnlTxId", reader.getElementText());

    public static final FieldProcessor ORGNL_E2E_ID_PROCESSOR = (reader, values, path) ->
            values.put("OrgnlEndToEndId", reader.getElementText());

    public static final FieldProcessor TX_ID_PROCESSOR = (reader, values, path) ->
            values.put("TxId", reader.getElementText());

    public static final FieldProcessor E2E_ID_PROCESSOR = (reader, values, path) ->
            values.put("EndToEndId", reader.getElementText());

    public static final FieldProcessor STS_DATE_TIME_PROCESSOR = (reader, values, path) ->
            values.put("acceptanceDateTime", reader.getElementText());

    public static final FieldProcessor AMOUNT_PROCESSOR = (reader, values, path) ->
            values.put("amount", reader.getElementText());

    public static final FieldProcessor BICFI_PROCESSOR = (reader, values, path) -> {
        String bicfiValue = reader.getElementText();
        String currentPath = getString(path);
        // Determine context from path

        if (currentPath.contains("DbtrAgt>FinInstnId>BICFI")) {
            values.put("debtorAgentBIC", bicfiValue);
        } else if (currentPath.contains("CdtrAgt>FinInstnId>BICFI")) {
            values.put("creditorAgentBIC", bicfiValue);
        } else if (currentPath.contains("InstgAgt>FinInstnId>BICFI")) {
            values.put("initiatingAgentBIC", bicfiValue);
        } else if (currentPath.contains("Assgnr>FinInstnId>BICFI")) {
            values.put("assignerAgentBIC", bicfiValue);
        } else if (currentPath.contains("Assgne>FinInstnId>BICFI")) {
            values.put("assigneeAgentBIC", bicfiValue);
        } else {
            values.put("genericBICFI", bicfiValue);
        }
    };

    public static final FieldProcessor IBAN_PROCESSOR = (reader, values, path) -> {
        String value = reader.getElementText();
        String currentPath = getString(path);
        if (currentPath.contains("DbtrAcct>Id>IBAN")) {
            values.put("debtorIBAN", value);
        } else if (currentPath.contains("CdtrAcct>Id>IBAN")) {
            values.put("creditorIBAN", value);
        } else {
            values.put("genericIBAN", value);
        }
    };

    private static String getString(Deque<String> path) {
        List<String> pathList = new ArrayList<>(path);
        Collections.reverse(pathList); // Now path is from outermost to innermost
        return String.join(">", pathList);
    }
}