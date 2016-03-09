package fr.ekinci.inmemory.test;

/**
 * Created by gekinci on 08/03/16.
 */
public class Dumb2 {
    private int id_d1;
    private int id_d2;
    private String attr4;

    public Dumb2(){

    }

    public Dumb2(int id_d1, int id_d2, String attr4) {
        this.id_d1 = id_d1;
        this.id_d2 = id_d2;
        this.attr4 = attr4;
    }

    public int getId_d1() {
        return id_d1;
    }

    public void setId_d1(int id_d1) {
        this.id_d1 = id_d1;
    }

    public int getId_d2() {
        return id_d2;
    }

    public void setId_d2(int id_d2) {
        this.id_d2 = id_d2;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }
}
