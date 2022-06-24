/**
 * @author Guilherme Azevedo dos Santos
 */

package utils;

import model.Medication;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    /**
     * implemented the singleton pattern, since this class is only
     * instanced once
     */
    private ArrayList<Medication> file = new ArrayList<>();

    private static FileReader instance = null;

    public static synchronized FileReader instanceOf(){
        if(instance == null){
            instance = new FileReader();
        }

        return instance;
    }

    private FileReader(){};

    /**
     * Reads the csv file, turns each line into an Medication object
     * and adds to the arrayList
     * @param name of the csv file
     * @return void
     */
    public void loadFile(String name) {
        String pathFileToLoad = "src/res/" + name + ".csv";

        String components;
        String ean1;
        String ean2;
        String ean3;
        String product;
        String presentation;
        String stripe;
        String pfWithoutTax;
        String pcmZeroPercent;
        String taxCreditList;
        String comerc2020;

        //starts the bufferedReader in charge of reading the csv with the path
        try (BufferedReader br = Files.newBufferedReader(Paths.get(pathFileToLoad), StandardCharsets.ISO_8859_1)) {
            String line = null;
            while ((line = br.readLine()) != null) {  // while the line is not empty
                String[] arr = new String[40]; // creates array with space for every single attribute
                int count = 0;
                line = line.replaceAll("\\;{2,}", ";"); // replaces repeated ; with a single one
                Scanner sc = new Scanner(line).useDelimiter(";"); // use ; as a delimiter for each word
                while (sc.hasNext()) { // while there is still words in the line
                    String word = sc.next();

                    /* if the word starts with an ", it means it's the beginning of the substance list
                    of the medication, that can contain multiple words
                     */
                    if (word.startsWith("\"")) {
                        /*
                        sets the first word with " as the start of the array, if the next words
                        don't contain an ending ", then you add the word to the same array with an
                        , before it. if it ends with an " then the component list ended and you can
                        go to the next word
                         */
                        arr[count] = word;
                        boolean isComponent = sc.hasNext() ? true : false;
                        while(isComponent && sc.hasNext()){
                            String nextWord = sc.next();
                            arr[0] += ", " + nextWord;
                            if(nextWord.endsWith("\"")){
                                isComponent = false;
                            }
                        }
                        count++;
                    }else {
                        arr[count] = word;
                        count++;
                    }
                }

                /*
                if for some reason any of the necessary information is null,
                put an empty string in it's place. here it's also done some necessary
                formatting in the components and product, for ease of finding them later
                 */
                components = arr[0] != null ? arr[0].replace("\"", "") : "";
                ean1 = arr[5] != null ? arr[5] : "";
                ean2 = arr[6] != null ? arr[6] : "";
                ean3 = arr[7] != null ? arr[7] : "";
                product = arr[8] != null ? arr[8].replaceAll("[\\p{P}&&[^\u0027]]", "") : "";
                presentation = arr[9] != null ? arr[9] : "";
                stripe = arr[38] != null ? arr[38] : "";
                pfWithoutTax = arr[13] != null ? arr[13] : "";
                pcmZeroPercent = arr[23] != null ? arr[23] : "";
                taxCreditList = arr[36] != null ? arr[36] : "";
                comerc2020 = arr[37] != null ? arr[37] : "";

                /* instances an medication object with the new information and adds to the
                arrayList */
                Medication medication =
                        new Medication(components, ean1, ean2, ean3, product , presentation,
                                stripe, pfWithoutTax, pcmZeroPercent, taxCreditList, comerc2020);

                file.add(medication);
            }
        } catch (IOException ioerr) {
            System.err.format("IO Error! Error: %s%n", ioerr);
        } catch (Exception err) {
            System.out.println("An error has occurred! Error: " + err);
        }
    }

    /**
     * Returns clone of arrayList populated by loadFile method
     * @return arrayList with medication objects
     */
    public ArrayList<Medication> getFile() {
        return (ArrayList<Medication>) file.clone();
    }
}
