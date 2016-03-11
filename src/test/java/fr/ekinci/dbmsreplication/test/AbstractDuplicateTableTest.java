package fr.ekinci.dbmsreplication.test;

import fr.ekinci.dbmsreplication.AbstractDuplicateTable;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by gekinci on 10/03/16.
 */
public class AbstractDuplicateTableTest {

    @Test
    public void testSqlFieldNames(){
        AbstractDuplicateTable dt = new AbstractDuplicateTable(){};
        try {
            List<String> fieldNames = dt.extractFieldNamesFromSqlQuery(" SeLeCt t1.field1 AS f1, t2.fie_ld2 As f_2   \n, Field as f3    FrOm myTable");
            Assert.assertEquals(fieldNames.get(0), "f1");
            Assert.assertEquals(fieldNames.get(1), "f_2");
            Assert.assertEquals(fieldNames.get(2), "f3");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
