import io.CSVReader;
import io.ConfigFile;
import io.ToFamiliar;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Main {

    // start with (mvn -q exec:java)
    public static void main(String[] args) throws IOException {
        System.out.println("Starting Composition Catalogue program");

        CSVReader reader = new CSVReader();
        Set<Map<String, String>> rawData = reader.contents();

        ConfigFile configuration = new ConfigFile();

        ToFamiliar transformer = new ToFamiliar(rawData, "Approach", configuration.criteria());
        Map<String, String> products = transformer.getProducts();

        products.forEach( (prod, contents) -> System.out.println(prod + " = " + contents) );


    }


}
