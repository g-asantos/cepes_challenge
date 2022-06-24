/**
 * @author Guilherme Azevedo dos Santos
 */

package model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MedicationList {
    ArrayList<Medication> medicationList;

    public MedicationList(ArrayList<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    /**
     * finds the medication on the list and prints relevant
     * information
     * @param name
     * @return void
     */
    public void findAndPrintMedicationByName(String name) {
        /*
          filters the medication list to contain only the medications
          sold in the year 2020 and the ones that the name matches the
          one passed by the user
         */
        List<Medication> foundMedications = medicationList
                .stream()
                .filter(m -> !m.getComerc2020().equals("Não"))
                .filter(m -> m.getProduct().equals(name))
                .collect(Collectors.toList());

        /*
          if any medication is found, prints the information of each one
          into the console, else prints product not found
         */
        if (foundMedications.size() > 0) {
            foundMedications.stream().forEach(m ->
                    System.out.println("Apresentacao: " + m.getPresentation()
                            + " | Produto: " + m.getProduct() + " | Nome: " + m.getComponents()
                            + " | valor PF sem impostos: " + m.getPfWithoutTax()));
        } else {
            System.out.println("Produto nao encontrado! Tenta novamente");
        }
    }

    /**
     * finds the medication by bar code, get's its name, searches for other
     * matches of the name, compares the pcm0% value between them and prints
     * the data
     * @param barCode
     * @return void
     */
    public void searchAndPrintMedicationByBarCode(String barCode) {
        /*
          maps the medication list to return only the ones that have either
          ean1,ean2 or ean3 information OR null, then filters again to remove all
          the null values, so the only ones remaining are the ones with some bar
          code information
         */
        List<Medication> eanMatchMedications = medicationList.stream().map(
                        m -> m.getEan1().equals(barCode) | m.getEan2().equals(barCode) |
                                m.getEan3().equals(barCode) ? m : null)
                .filter(m -> m != null)
                .collect(Collectors.toList());

        /*
         if any medication with barcode is found, gets its name, and searches the
         medication list for all its matches.
         afterwards, compares each one by the pcm0% information, ordering them
         from highest to lowest value
       */
        if (eanMatchMedications.size() > 0) {
            List<Medication> allRegisteredMedications = medicationList.stream()
                    .filter(m -> m.getProduct().equals(eanMatchMedications.get(0).getProduct()))
                    .collect(Collectors.toList());

            Collections.sort(allRegisteredMedications, (m1, m2) ->
                    Double.compare(Double.parseDouble(m2.getPcmZeroPercent()
                                    .replace(",", ".")),
                            Double.parseDouble(m1.getPcmZeroPercent()
                                    .replace(",", "."))));
            Double lowestValue = Double
                    .parseDouble(allRegisteredMedications.get(allRegisteredMedications.size() - 1)
                            .getPcmZeroPercent().replace(",", "."));
            Double highestValue = Double
                    .parseDouble(allRegisteredMedications.get(0)
                            .getPcmZeroPercent().replace(",", "."));

            System.out.println("O valor mais alto é " + highestValue + ", e o valor mais baixo é "
                    + lowestValue
                    + ", e a diferença entre eles é " + (highestValue - lowestValue));
        } else {
            System.out.println("Produto com esse codigo de barras nao encontrado! Tente" +
                    " novamente");
        }
    }

    /**
     * prints the tax credit comparative list
     * @return void
     */
    public void printTaxCreditList() {
         /*
          filters the meditation list 3 times, to get 3 diferent lists,
          one for negative credit, one for positive credit and one for neutral
          credit
        */
        List<Medication> positiveCreditTax = medicationList.stream()
                .filter(m -> !m.getComerc2020().equals("Não"))
                .filter(m -> m.getTaxCreditList().equals("Positiva"))
                .collect(Collectors.toList());
        List<Medication> negativeCreditTax = medicationList.stream()
                .filter(m -> !m.getComerc2020().equals("Não"))
                .filter(m -> m.getTaxCreditList().equals("Negativa"))
                .collect(Collectors.toList());
        List<Medication> neutralCreditTax = medicationList.stream()
                .filter(m -> !m.getComerc2020().equals("Não"))
                .filter(m -> m.getTaxCreditList().equals("Neutra"))
                .collect(Collectors.toList());

        Integer totalSize = medicationList.size();

        /*
        gets the percentage of each one of the tax credit types, then rounds them
        to limit the decimal number limited to 2
        */
        Double positivePercentage = positiveCreditTax.size() * 100 / totalSize.doubleValue();
        Double negativePercentage = negativeCreditTax.size() * 100 / totalSize.doubleValue();
        Double neutralPercentage = (neutralCreditTax.size() * 100) / totalSize.doubleValue();

        Double roundedPositivePercentage = Math.round(positivePercentage * 100.0) / 100.0;
        Double roundedNegativePercentage = Math.round(negativePercentage * 100.0) / 100.0;
        Double roundedNeutralPercentage = Math.round(neutralPercentage * 100.0) / 100.0;

        String positivePercentageAsterisks = "";
        String negativePercentageAsterisks = "";
        String neutralPercentageAsterisks = "";

        /*
        adds an asterisk to the asterisk string for each
        full % point of the task credit type
        */
        for (int i = 1; i < roundedPositivePercentage; i++) {
            positivePercentageAsterisks += "*";
        }
        for (int i = 1; i < roundedNegativePercentage; i++) {
            negativePercentageAsterisks += "*";
        }
        for (int i = 1; i < roundedNeutralPercentage; i++) {
            neutralPercentageAsterisks += "*";
        }

        Double totalPercentage = roundedNegativePercentage +
                roundedPositivePercentage + roundedNeutralPercentage;
        System.out.println("CLASSIFICAÇÃO  " + " PERCENTUAL " + "  GRÁFICO");
        System.out.println("Negativa  " + "      "
                + roundedNegativePercentage + "%" + "       " + negativePercentageAsterisks);
        System.out.println("Neutra  " + "        "
                + roundedNeutralPercentage + "%" + "       " + neutralPercentageAsterisks);
        System.out.println("Positiva  " + "      "
                + roundedPositivePercentage + "%" + "       " + positivePercentageAsterisks);
        System.out.println("TOTAL           " + totalPercentage + "%");
    }
}
