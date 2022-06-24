/**
 * @author Guilherme Azevedo dos Santos
 */

package controller;

import model.MedicationList;
import utils.FileReader;
import model.Medication;

import java.util.*;

public class MedicationInfoController {

    /**
     * implemented the singleton pattern, since this class is only
     * instanced once
     */
    private static MedicationInfoController instance = null;

    public static synchronized MedicationInfoController instanceOf(){
        if (instance == null ) {
            instance = new MedicationInfoController();
        }
        return instance;
    }
    private MedicationInfoController() {
    }

    /**
     * implements the visual interface the user can interact
     * @return void
     */
    public void execute() {

        Scanner in = new Scanner(System.in);

        boolean isRunning = true;

        FileReader reader = FileReader.instanceOf();

        reader.loadFile("TA_PRECO_MEDICAMENTO");

        ArrayList<Medication> medicationArrayList = reader.getFile();

        MedicationList medicationList = new MedicationList(medicationArrayList);

        System.out.println("Bem vindo ao programa de apresentacao de preços de medicamentos!");
        System.out.println("Digite uma das seguintes opçoes a seguir:");
        while (isRunning) {
            System.out.println("1: Consultar medicamento pelo nome");
            System.out.println("2: Buscar pelo codigo de barras");
            System.out.println("3: Consultar comparativo da lista de concessao de credito tributario");
            System.out.println("4: Sair");
            try {
                Integer choice = Integer.parseInt(in.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Digite o nome do medicamento");
                        String name = in.nextLine();

                        // removes any unexpected symbols and turns string passed to uppercase
                        String formattedName = name.toUpperCase().replaceAll("[\\p{P}&&[^\u0027]]", "");

                        medicationList.findAndPrintMedicationByName(formattedName);
                        break;
                    case 2:
                        System.out.println("Insira o numero do codigo de barras procurado");
                        String barCode = in.nextLine();
                        medicationList.searchAndPrintMedicationByBarCode(barCode);
                        break;
                    case 3:
                        medicationList.printTaxCreditList();
                        break;
                    case 4:
                        isRunning = false;
                        in.close();
                        break;
                }
            } catch (NumberFormatException formaterr) {
                System.out.println("Foi inserido um valor de tipo diferente do esperado! Tente novamente");
            } catch (Exception err) {
                System.out.println(err);
            }
        }
    }
}
