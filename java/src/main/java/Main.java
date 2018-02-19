import fr.familiar.interpreter.FMLShell;
import fr.familiar.variable.FeatureModelVariable;
import io.*;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class Main {

    private static CommandLine arguments;

    // start with (mvn -q exec:java)
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Composition Catalogue program");
        readParameters(args);

        System.out.println("Reading catalogue CSV file");
        String productFile = arguments.getOptionValue("csv");
        CSVReader reader = ( productFile == null? new CSVReader() : new CSVReader(new File(productFile)));
        Set<Map<String, String>> rawData = reader.contents();

        System.out.println("Transforming each line into individual products");
        String featureFile = arguments.getOptionValue("features");
        ConfigFile configuration = (featureFile == null? new ConfigFile(): new ConfigFile(new File(featureFile)));
        ToFamiliar transformer = new ToFamiliar(rawData, "Approach", configuration.criteria());
        Map<String, String> products = transformer.getProducts();

        System.out.println("Merging products into a single catalogue");
        FeatureModelVariable catalogue = buildCatalogue(products);
        System.out.println("Number of products: " + catalogue.counting());
    }

    private static void readParameters(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("csv", true, "CSV file containing the products");
        options.addOption("features", true, "TXT file containing the feature descriptions");
        CommandLineParser parser = new DefaultParser();
        arguments = parser.parse( options, args);
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
