package fr.ekinci.inmemory.test;

import fr.ekinci.dbmsreplication.inmemorysql.InMemorySQL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gekinci on 08/03/16.
 */
public class MainTest {

    public static void main(String[] args){
        List<Dumb1> ldb1 = new ArrayList<>();
        // int id_d1, double attr2, long attr3
        ldb1.add(new Dumb1(1, 91, 92));
        ldb1.add(new Dumb1(2, 93, 94));
        ldb1.add(new Dumb1(3, 95, 96));

        List<Dumb2> ldb2 = new ArrayList<>();
        // int id_d1, int id_d2, String attr4
        ldb2.add(new Dumb2(1, 1, "Toto"));
        ldb2.add(new Dumb2(1, 2, "Tata"));
        ldb2.add(new Dumb2(1, 3, "Tutu"));
        ldb2.add(new Dumb2(2, 4, "Titi"));

        List<Dumb3> ldb3 = new ArrayList<>();
        // int id_d2, int id_d3, double attr5
        ldb3.add(new Dumb3(1, 1, 97));
        ldb3.add(new Dumb3(1, 2, 98));
        ldb3.add(new Dumb3(2, 3, 99));

        // Request
        String sqlRequest = "SELECT t1.id_d1, t1.attr2, t1.attr3, t2.id_d2, t2.attr4, t3.id_d3, t3.attr5 " +
                " FROM t1" +
                " LEFT JOIN t2" +
                " ON t1.id_d1 = t2.id_d1" +
                " LEFT JOIN t3" +
                " ON t2.id_d2 = t3.id_d3" +
                " ORDER BY t1.id_d1, t2.id_d2, t3.id_d3";

        InMemorySQL memory = new InMemorySQL();
        try {
            Collection<ReturnDumb> result = memory.add(Dumb1.class, ldb1)
                    .add(Dumb2.class, ldb2)
                    .add(Dumb3.class, ldb3)
                    .executeQuery(ReturnDumb.class, sqlRequest);

            for(ReturnDumb r : result){
                System.out.println(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
