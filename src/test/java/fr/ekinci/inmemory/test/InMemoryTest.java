package fr.ekinci.inmemory.test;

import fr.ekinci.dbmsreplication.inmemorysql.InMemorySQL;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gekinci on 08/03/16.
 */
public class InMemoryTest {

    @Test
    public void testOccurences(){
        /*
        Collection<Dumb1> col1 = new ArrayList<>();
        for(int i = 0, max = 2000; i < max; i++){
            col1.add(new Dumb1(1,0,0));
        }

        Collection<Dumb2> col2 = new ArrayList<>();
        for(int i = 0, max = 4000; i < max; i++){
            col2.add(new Dumb2(1,0,""));
        }

        String sqlRequest = "SELECT t1.id_d1 FROM t1 LEFT JOIN t2 ON t1.id_d1 = t2.id_d1";

        try {
            Collection<ReturnDumb> result = new InMemorySQL()
                    .add(Dumb1.class, col1)
                    .add(Dumb2.class, col2)
                    .executeQuery(ReturnDumb.class, sqlRequest);
            Assert.assertEquals(result.size(), 4000);
        } catch (SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        */
    }
}
