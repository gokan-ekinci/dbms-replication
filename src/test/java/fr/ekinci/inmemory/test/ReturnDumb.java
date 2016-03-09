package fr.ekinci.inmemory.test;

import java.math.BigDecimal;

/**
 * Created by gekinci on 08/03/16.
 */
public class ReturnDumb {
    // From Dumb1
    private Integer id_d1;
    private BigDecimal attr2;
    private Integer attr3;

    // From Dumb2
    private Integer id_d2;
    private String attr4;

    // From Dumb3
    private Integer id_d3;
    private BigDecimal attr5;

    public ReturnDumb(){

    }

    @Override
    public String toString() {
        return "ReturnDumb{" +
                "id_d1=" + id_d1 +
                ", attr2=" + attr2 +
                ", attr3=" + attr3 +
                ", id_d2=" + id_d2 +
                ", attr4='" + attr4 + '\'' +
                ", id_d3=" + id_d3 +
                ", attr5=" + attr5 +
                '}';
    }

    public Integer getId_d1() {
        return id_d1;
    }

    public void setId_d1(Integer id_d1) {
        this.id_d1 = id_d1;
    }

    public BigDecimal getAttr2() {
        return attr2;
    }

    public void setAttr2(BigDecimal attr2) {
        this.attr2 = attr2;
    }

    public Integer getAttr3() {
        return attr3;
    }

    public void setAttr3(Integer attr3) {
        this.attr3 = attr3;
    }

    public Integer getId_d2() {
        return id_d2;
    }

    public void setId_d2(Integer id_d2) {
        this.id_d2 = id_d2;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public Integer getId_d3() {
        return id_d3;
    }

    public void setId_d3(Integer id_d3) {
        this.id_d3 = id_d3;
    }

    public BigDecimal getAttr5() {
        return attr5;
    }

    public void setAttr5(BigDecimal attr5) {
        this.attr5 = attr5;
    }
}
