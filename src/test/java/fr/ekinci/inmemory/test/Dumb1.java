package fr.ekinci.inmemory.test;

/**
 * Created by gekinci on 08/03/16.
 */
public class Dumb1 {
    private int id_d1;
    private double attr2;
    private long attr3;

    public Dumb1(){

    }

    public Dumb1(int id_d1, double attr2, long attr3) {
        this.id_d1 = id_d1;
        this.attr2 = attr2;
        this.attr3 = attr3;
    }



    public int getId_d1() {
        return id_d1;
    }

    public void setId_d1(int id_d1) {
        this.id_d1 = id_d1;
    }

    public double getAttr2() {
        return attr2;
    }

    public void setAttr2(double attr2) {
        this.attr2 = attr2;
    }

    public long getAttr3() {
        return attr3;
    }

    public void setAttr3(long attr3) {
        this.attr3 = attr3;
    }
}
