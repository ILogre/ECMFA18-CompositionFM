package io;

import org.apache.commons.csv.*;
import java.io.*;
import java.util.*;

public class CSVReader {

    private File raw;

    public CSVReader(File rawFile) { this.raw = rawFile; }

    public CSVReader() {
        this(new File(CSVReader.class.getClassLoader().getResource("products.csv").getFile()));
    }

    public Set<Map<String, String>> contents() throws IOException {
        Reader reader =  new InputStreamReader(new FileInputStream(raw));
        CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withDelimiter(';').withHeader());
        try {
            Set<String> criteria = parser.getHeaderMap().keySet();
            Set<Map<String, String>> result = new HashSet<>();
            for(CSVRecord record : parser) {
                Map<String, String> product = readProduct(criteria, record);
                result.add(product);
            }
            return result;
        }
        finally {
            reader.close();
            parser.close();
        }
    }

    private Map<String, String> readProduct(Set<String> criteria, CSVRecord record) {
        Map<String, String> product = new HashMap<>();
        for(String c: criteria)
            product.put(c,record.get(c));
        return product;
    }

}
