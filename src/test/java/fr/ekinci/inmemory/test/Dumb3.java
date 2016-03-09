package fr.ekinci.inmemory.test;

/**
 * Created by gekinci on 08/03/16.
 */
public class Dumb3 {
    private int id_d2;
    private int id_d3;
    private double attr5;

    public Dumb3(){

    }

    public Dumb3(int id_d2, int id_d3, double attr5) {
        this.id_d2 = id_d2;
        this.id_d3 = id_d3;
        this.attr5 = attr5;
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
}
