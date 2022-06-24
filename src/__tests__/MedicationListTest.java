/**
 * @author Guilherme Azevedo dos Santos
 */

package __tests__;
import model.Medication;
import model.MedicationList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicationListTest {
    private MedicationList medicationList;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private  PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() throws Exception{
        FileReader reader = FileReader.instanceOf();

        reader.loadFile("mock");
        ArrayList<Medication> medArr = new ArrayList<>();
        medArr.add(reader.getFile().get(1));
        medicationList = new MedicationList(medArr);
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


    @Test
    public void findAndPrintMedicationByName(){
        medicationList.findAndPrintMedicationByName("VACINA VARICELA ATENUADA");
        String returnString = "Apresentacao: 1350 UFP PO LIOF SOL INJ CT  FA VD INC + FA VD INC DIL X 0,7 ML " +
                "| Produto: VACINA VARICELA ATENUADA | Nome: VIRUS DA VARICELA " +
                "| valor PF sem impostos: 103,04" +
                "\n";
        String test = outContent.toString();
        assertEquals(returnString, outContent.toString());
    }

    @Test
    public void searchAndPrintMedicationByBarCode(){
        medicationList.searchAndPrintMedicationByBarCode("7897337703345");
        String returnString = "O valor mais alto é 142.45, e o valor mais baixo é 142.45, e a diferença entre eles é 0.0"
                + "\n";
        assertEquals(returnString, outContent.toString());
    }

    @Test
    public void printTaxCreditList(){
        medicationList.printTaxCreditList();
        String returnString = "CLASSIFICAÇÃO   PERCENTUAL   GRÁFICO\n" +
                "Negativa        0.0%       \n" +
                "Neutra          0.0%       \n" +
                "Positiva        100.0%       ***************************************************************************************************\n" +
                "TOTAL           100.0%" +
                "\n";
        assertEquals(returnString, outContent.toString());
    }
}
