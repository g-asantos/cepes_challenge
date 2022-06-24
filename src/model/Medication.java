/**
 * @author Guilherme Azevedo dos Santos
 */

package model;

public class Medication {

    private String components;
    private String ean1;
    private String ean2;
    private String ean3;
    private String product;
    private String presentation;
    private String stripe;
    private String pfWithoutTax;
    private String pcmZeroPercent;
    private String taxCreditList;
    private String comerc2020;

    public Medication(String components, String ean1, String ean2, String ean3, String product, String presentation, String stripe, String pfWithoutTax, String pcmZeroPercent, String taxCreditList, String comerc2020) {
        this.components = components;
        this.ean1 = ean1;
        this.ean2 = ean2;
        this.ean3 = ean3;
        this.product = product;
        this.presentation = presentation;
        this.stripe = stripe;
        this.pfWithoutTax = pfWithoutTax;
        this.pcmZeroPercent = pcmZeroPercent;
        this.taxCreditList = taxCreditList;
        this.comerc2020 = comerc2020;
    }

    public String getComponents() {
        return components;
    }

    public String getEan1() {
        return ean1;
    }

    public String getEan2() {
        return ean2;
    }

    public String getEan3() {
        return ean3;
    }

    public String getProduct() {
        return product;
    }

    public String getPresentation() {
        return presentation;
    }

    public String getPfWithoutTax() {
        return pfWithoutTax;
    }

    public String getPcmZeroPercent() {
        return pcmZeroPercent;
    }

    public String getTaxCreditList() {
        return taxCreditList;
    }

    public String getComerc2020() {
        return comerc2020;
    }

}
