package fr.ekinci.dbmsreplication.test;

import java.sql.Timestamp;

/**
 * Created by gekinci on 08/03/16.
 */
public class Dumb3 {
    private int id_d2;
    private int id_d3;
    private double attr5;

    private Double attr6;
    private float attr7;
    private Float attr8;
    private byte attr9;
    private short attr10;
    private int attr11;
    private Byte attr12;
    private Short attr13;
    private Integer attr14;
    // private char attr15;      // Forbidden
    // private Character attr16; // Forbidden
    private String attr17;
    private long attr18;
    private Long attr19;
    private Timestamp attr20;

    public Dumb3(){

    }

    public Dumb3(int id_d2, int id_d3, double attr5){
        this.id_d2 = id_d2;
        this.id_d3 = id_d3;
        this.attr5 = attr5;
    }

    public Dumb3(int id_d2, int id_d3, double attr5, Double attr6, float attr7, Float attr8, byte attr9, short attr10, int attr11, Byte attr12, Short attr13, Integer attr14 /*, char attr15 , Character attr16*/, String attr17, long attr18, Long attr19, Timestamp attr20) {
        this.id_d2 = id_d2;
        this.id_d3 = id_d3;
        this.attr5 = attr5;
        this.attr6 = attr6;
        this.attr7 = attr7;
        this.attr8 = attr8;
        this.attr9 = attr9;
        this.attr10 = attr10;
        this.attr11 = attr11;
        this.attr12 = attr12;
        this.attr13 = attr13;
        this.attr14 = attr14;
        // this.attr15 = attr15;
        // this.attr16 = attr16;
        this.attr17 = attr17;
        this.attr18 = attr18;
        this.attr19 = attr19;
        this.attr20 = attr20;
    }

    public int getId_d2() {
        return id_d2;
    }

    public void setId_d2(int id_d2) {
        this.id_d2 = id_d2;
    }

    public int getId_d3() {
        return id_d3;
    }

    public void setId_d3(int id_d3) {
        this.id_d3 = id_d3;
    }

    public double getAttr5() {
        return attr5;
    }

    public void setAttr5(double attr5) {
        this.attr5 = attr5;
    }

    public Double getAttr6() {
        return attr6;
    }

    public void setAttr6(Double attr6) {
        this.attr6 = attr6;
    }

    public float getAttr7() {
        return attr7;
    }

    public void setAttr7(float attr7) {
        this.attr7 = attr7;
    }

    public Float getAttr8() {
        return attr8;
    }

    public void setAttr8(Float attr8) {
        this.attr8 = attr8;
    }

    public byte getAttr9() {
        return attr9;
    }

    public void setAttr9(byte attr9) {
        this.attr9 = attr9;
    }

    public short getAttr10() {
        return attr10;
    }

    public void setAttr10(short attr10) {
        this.attr10 = attr10;
    }

    public int getAttr11() {
        return attr11;
    }

    public void setAttr11(int attr11) {
        this.attr11 = attr11;
    }

    public Byte getAttr12() {
        return attr12;
    }

    public void setAttr12(Byte attr12) {
        this.attr12 = attr12;
    }

    public Short getAttr13() {
        return attr13;
    }

    public void setAttr13(Short attr13) {
        this.attr13 = attr13;
    }

    public Integer getAttr14() {
        return attr14;
    }

    public void setAttr14(Integer attr14) {
        this.attr14 = attr14;
    }

    /*
    public char getAttr15() {
        return attr15;
    }

    public void setAttr15(char attr15) {
        this.attr15 = attr15;
    }


    public Character getAttr16() {
        return attr16;
    }

    public void setAttr16(Character attr16) {
        this.attr16 = attr16;
    }
    */

    public String getAttr17() {
        return attr17;
    }

    public void setAttr17(String attr17) {
        this.attr17 = attr17;
    }

    public long getAttr18() {
        return attr18;
    }

    public void setAttr18(long attr18) {
        this.attr18 = attr18;
    }

    public Long getAttr19() {
        return attr19;
    }

    public void setAttr19(Long attr19) {
        this.attr19 = attr19;
    }

    public Timestamp getAttr20() {
        return attr20;
    }

    public void setAttr20(Timestamp attr20) {
        this.attr20 = attr20;
    }
}
