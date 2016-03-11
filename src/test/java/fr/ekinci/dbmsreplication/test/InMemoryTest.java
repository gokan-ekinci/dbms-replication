package fr.ekinci.dbmsreplication.test;

import fr.ekinci.dbmsreplication.inmemorysql.InMemorySQL;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

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
        } catch (SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }
}
