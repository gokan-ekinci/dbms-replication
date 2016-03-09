package fr.ekinci.dbmsreplication;

import fr.ekinci.dbmsreplication.fieldtype.FieldType;
import fr.ekinci.dbmsreplication.generics.GenericsUtils;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that factorize some replication methods
 *
 * @author Gokan EKINCI
 */
public abstract class AbstractDuplicateTable {

    /**
     * Fill the SQLMetaDataColumn list (gives a complete MetaData tuple) from a connection and a table name
     *
     * @param sourceConnection
     * @param sourceTableName
     * @return
     * @throws SQLException
     */
    public List<SQLMetaDataColumn> generateSQLMetaDataTuple(Connection sourceConnection, String sourceTableName) throws SQLException {
        List<SQLMetaDataColumn> list = new ArrayList<SQLMetaDataColumn>();

        Statement stmt = sourceConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + sourceTableName);
        ResultSetMetaData rmd = rs.getMetaData();

        int nbColumn = rmd.getColumnCount(); // Number of columns in table
        for(int i=0; i < nbColumn; i++){
            SQLMetaDataColumn smdt = new SQLMetaDataColumn();
            smdt.setColumnName(rmd.getColumnName(i+1));
            smdt.setJavaTypeId(rmd.getColumnType(i+1));
            // Test line : System.out.println(rmd.getColumnType(i+1) + " " + rmd.getColumnTypeName(i+1));
            list.add(smdt);
        }

        rs.close();
        stmt.close();

        return list;
    }


    /**
     * Fill the SQLMetaDataColumn list (gives a complete MetaData tuple) from a Class<T>
     *
     * @param clazz
     * @return
     */
    public List<SQLMetaDataColumn> generateSQLMetaDataTuple(Class<?> clazz) {
        List<SQLMetaDataColumn> list = new ArrayList<SQLMetaDataColumn>();

        for(Field f : GenericsUtils.getAllDeclaredFields(new ArrayList<>(), clazz)){
            f.setAccessible(true);
            SQLMetaDataColumn smdt = new SQLMetaDataColumn();
            smdt.setColumnName(f.getName());
            // f.getClass() returns Class<Field>, f.getDeclaringClass() returns attribute's Class
            smdt.setGenericDBFieldType(FieldType.genericFieldType(f.getType()));
            list.add(smdt);
        }

        return list;
    }
}
