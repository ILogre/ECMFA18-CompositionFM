package utils;

import java.io.*;

public class CSV2FM_Composition {

    // The goal of this code is CLEARLY not to be optimised
    // but instead understandable by anyone else than me
    // Yes, there is smarter, faster, prettier ways to do it
    // The effort I put in it was proportional to it goal :
    // open a CSV file and extract the FMs of composition operators.
    public static void transformCSV2FM(String inputCSV,int nbCol,int nbLig) throws IOException {

        String inputPath = "src/main/resources/"+inputCSV+".csv";

        File outputFm = new File("src/main/resources/"+inputCSV+"_fms_functions.fml");
        if(!outputFm.exists())
            try {
                outputFm.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        String outputFmPath = outputFm.getPath();


        // Initialize the array
        String[][] table = new String[nbLig][nbCol];
        int i,j;

        // Fill the array from the csv file
        InputStream ips = new FileInputStream(inputPath);
        InputStreamReader ipsr = new InputStreamReader(ips);
        BufferedReader br = new BufferedReader(ipsr);
        String ligne;
        i = 0;
        while ((ligne = br.readLine()) != null) {
            j = 0;
            for (String mot : ligne.split(";"))
                if(j<nbCol)
                    table[i][j++] = mot;
            i++;
        }
        br.close();


        StringBuilder fms = new StringBuilder();
        for(int c=1;c<nbCol;c++){
            // retrieve the operator name
            fms.append("fm_"+ table[0][c] +" = ");

            // Here we suppress the Name to know the real number of constraints
            fms.append("FM(CompositionOperator: Family Level Conformity Role Automation Consistency; ");

            int counter = 1;

            // Family
            fms.append("Family:");
            String family = "";
            for(int l = counter;l<=5;l++) {
                family = table[l][0];
                if(table[l][c].equals("Oui"))
                    fms.append(" "+family);
            }
            fms.append(";");

            //Level
            counter=6;
            fms.append("Level:");
            fms.append(" "+table[counter][c]+";");

            //Conformity
            counter=7;
            fms.append("Conformity:");
            fms.append(" "+table[counter][c]+";");

            //Role
            counter=8;
            fms.append("Role:");
            fms.append(" "+table[counter][c]+";");

            //Arity
            counter=9;
            fms.append("Arity:");
            fms.append(" "+table[counter][c]+";");

            //Automation
            counter=10;
            fms.append("Automation: ");
            fms.append(" '"+table[counter][c]+"';");

            //Consistency
            counter=11;
            fms.append("Consistency:");
            for(int l = counter;l<nbLig;l++) {
                if(table[l][c].equals("Oui"))
                    fms.append(" "+table[l][0]);
            }
            fms.append(";)\n");
        }
        try{
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFmPath), "utf-8"));
            writer.write(fms.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
