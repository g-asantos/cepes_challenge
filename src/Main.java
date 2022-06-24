/**
 * @author Guilherme Azevedo dos Santos
 */

import controller.MedicationInfoController;

public class Main {
    public static void main(String[] args) {
        MedicationInfoController medController = MedicationInfoController.instanceOf();
        medController.execute();
    }
}
