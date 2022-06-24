/**
 * @author Guilherme Azevedo dos Santos
 */

package __tests__;

import model.Medication;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import utils.FileReader;

import java.util.ArrayList;

public class FileReaderTest {
    private ArrayList<Medication> medicationList;

    @BeforeEach
    public void setUp() throws Exception{
        FileReader reader = FileReader.instanceOf();

        reader.loadFile("mock");

        medicationList = reader.getFile();
    }

    @Test
    public void getComponents(){
        String name = medicationList.get(1).getComponents();
        assertEquals("VIRUS DA VARICELA", name);
    }
    @Test
    public void getEan1(){
        String ean1 = medicationList.get(1).getEan1();
        assertEquals("7897337703345", ean1);
    }
    @Test
    public void getEan2(){
        String ean2 = medicationList.get(1).getEan2();
        assertEquals("    -     ", ean2);
    }
    @Test
    public void getEan3(){
        String ean3 = medicationList.get(1).getEan3();
        assertEquals("    -     ", ean3);
    }
    @Test
    public void getProduct(){
        String product = medicationList.get(1).getProduct();
        assertEquals("VACINA VARICELA ATENUADA", product);
    }
    @Test
    public void getPresentation(){
        String presentation = medicationList.get(1).getPresentation();
        assertEquals("1350 UFP PO LIOF SOL INJ CT  FA VD INC + FA VD INC DIL X 0,7 ML", presentation);
    }
    @Test
    public void getPfWithoutTax(){
        String pfWithoutTax = medicationList.get(1).getPfWithoutTax();
        assertEquals("103,04", pfWithoutTax);
    }
    @Test
    public void getPmcZeroPercent(){
        String pmcZeroPercent = medicationList.get(1).getPcmZeroPercent();
        assertEquals("142,45", pmcZeroPercent);
    }
    @Test
    public void getTaxCreditList(){
        String taxCreditList = medicationList.get(1).getTaxCreditList();
        assertEquals("Positiva", taxCreditList);
    }
    @Test
    public void getComerc2020(){
        String comerc2020 = medicationList.get(1).getComerc2020();
        assertEquals("Sim", comerc2020);
    }
}
