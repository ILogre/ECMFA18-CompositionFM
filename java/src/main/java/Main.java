import fr.familiar.interpreter.FMLShell;
import fr.familiar.parser.FMBuilder;
import fr.familiar.parser.FMLCommandInterpreter;
import fr.familiar.variable.FeatureModelVariable;
import io.CSVReader;
import io.ConfigFile;
import io.ToFamiliar;
import sun.util.resources.cldr.en.CalendarData_en_CA;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Main {

    // start with (mvn -q exec:java)
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Composition Catalogue program");

        System.out.println("Reading catalogue CSV file");
        CSVReader reader = new CSVReader();
        Set<Map<String, String>> rawData = reader.contents();

        System.out.println("Transforming each line into individual products");
        ConfigFile configuration = new ConfigFile();
        ToFamiliar transformer = new ToFamiliar(rawData, "Approach", configuration.criteria());
        Map<String, String> products = transformer.getProducts();

        System.out.println("Merging products into a single catalogue");
        FeatureModelVariable catalogue = buildCatalogue(products);
        System.out.println("Number of products: " + catalogue.counting());
    }

    private static FeatureModelVariable buildCatalogue(Map<String, String> products) throws Exception {
        FMLShell shell = FMLShell.instantiateStandalone(null);
        shell.parse(asFML(products));
        return (FeatureModelVariable) shell.getCurrentEnv().getVariable("catalogue");
    }

    private static String asFML(Map<String, String> products) {
        StringBuffer buff = new StringBuffer();
        products.forEach( (prod, contents) -> buff.append(prod).append(" = ").append(contents).append("\n") );
        buff.append("catalogue = merge sunion prod*\n");
        return buff.toString();
    }

}
