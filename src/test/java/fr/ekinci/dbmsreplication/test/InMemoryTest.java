package fr.ekinci.dbmsreplication.test;

import fr.ekinci.dbmsreplication.inmemorysql.InMemorySQL;
import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gekinci on 08/03/16.
 */
public class InMemoryTest {

    @Test
    public void testOccurences(){
        int expectedNumberOfTuples = 10_000;
        Collection<Dumb1> col1 = new ArrayList<>();
        col1.add(new Dumb1(1,0,0));


        Collection<Dumb2> col2 = new ArrayList<>();
        for(int i = 0, max = expectedNumberOfTuples; i < max; i++){
            col2.add(new Dumb2(1,0,""));
        }

        String sqlRequest = "SELECT t1.id_d1 FROM t1 LEFT JOIN t2 ON t1.id_d1 = t2.id_d1";

        try {
            Instant start = Instant.now();
            Collection<ReturnDumb> result = new InMemorySQL()
                    .add(Dumb1.class, col1)
                    .add(Dumb2.class, col2)
                    .executeQuery(ReturnDumb.class, sqlRequest);
            System.out.println("Duration in nano: " + Duration.between(start, Instant.now()).getNano());
            Assert.assertEquals(result.size(), expectedNumberOfTuples);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testResult() throws SQLException {
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
        String sqlRequest = "SELECT t1.id_d1 AS id_d1, t1.attr2 AS attr2, t1.attr3 AS attr3, t2.id_d2 AS id_d2, t2.attr4 AS attr4, t3.id_d3 AS id_d3, t3.attr5 AS attr5 " +
                " FROM t1" +
                " LEFT JOIN t2" +
                " ON t1.id_d1 = t2.id_d1" +
                " LEFT JOIN t3" +
                " ON t2.id_d2 = t3.id_d3" +
                " ORDER BY t1.id_d1, t2.id_d2, t3.id_d3";

        List<ReturnDumb> result = new InMemorySQL().add(Dumb1.class, ldb1)
                .add(Dumb2.class, ldb2)
                .add(Dumb3.class, ldb3)
                .executeQuery(ReturnDumb.class, sqlRequest);

        // Size :
        Assert.assertEquals(result.size(), 5);

        // Tuple 1
        Assert.assertEquals(result.get(0).getId_d1(), new Integer(1));
        Assert.assertEquals(result.get(0).getAttr2(), new BigDecimal("91"));
        Assert.assertEquals(result.get(0).getAttr3(), new Long(92));
        Assert.assertEquals(result.get(0).getId_d2(), new Integer(1));
        Assert.assertEquals(result.get(0).getAttr4(), "Toto");
        Assert.assertEquals(result.get(0).getId_d3(), new Integer(1));
        Assert.assertEquals(result.get(0).getAttr5(), new BigDecimal("97"));

        // Tuple 2
        Assert.assertEquals(result.get(1).getId_d1(), new Integer(1));
        Assert.assertEquals(result.get(1).getAttr2(), new BigDecimal("91"));
        Assert.assertEquals(result.get(1).getAttr3(), new Long(92));
        Assert.assertEquals(result.get(1).getId_d2(), new Integer(2));
        Assert.assertEquals(result.get(1).getAttr4(), "Tata");
        Assert.assertEquals(result.get(1).getId_d3(), new Integer(2));
        Assert.assertEquals(result.get(1).getAttr5(), new BigDecimal("98"));

        // Tuple 3
        Assert.assertEquals(result.get(2).getId_d1(), new Integer(1));
        Assert.assertEquals(result.get(2).getAttr2(), new BigDecimal("91"));
        Assert.assertEquals(result.get(2).getAttr3(), new Long(92));
        Assert.assertEquals(result.get(2).getId_d2(), new Integer(3));
        Assert.assertEquals(result.get(2).getAttr4(), "Tutu");
        Assert.assertEquals(result.get(2).getId_d3(), new Integer(3));
        Assert.assertEquals(result.get(2).getAttr5(), new BigDecimal("99"));

        // Tuple 4
        Assert.assertEquals(result.get(3).getId_d1(), new Integer(2));
        Assert.assertEquals(result.get(3).getAttr2(), new BigDecimal("93"));
        Assert.assertEquals(result.get(3).getAttr3(), new Long(94));
        Assert.assertEquals(result.get(3).getId_d2(), new Integer(4));
        Assert.assertEquals(result.get(3).getAttr4(), "Titi");
        Assert.assertNull(result.get(3).getId_d3());
        Assert.assertNull(result.get(3).getAttr5());

        // Tuple 5
        Assert.assertEquals(result.get(4).getId_d1(), new Integer(3));
        Assert.assertEquals(result.get(4).getAttr2(), new BigDecimal("95"));
        Assert.assertEquals(result.get(4).getAttr3(), new Long(96));
        Assert.assertNull(result.get(4).getId_d2());
        Assert.assertNull(result.get(4).getAttr4());
        Assert.assertNull(result.get(4).getId_d3());
        Assert.assertNull(result.get(4).getAttr5());

        for(ReturnDumb r : result){
            System.out.println(r);
        }
    }
}
