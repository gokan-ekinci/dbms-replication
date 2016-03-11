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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that factorize some replication methods
 *
 * @author Gokan EKINCI
 */
public abstract class AbstractDuplicateTable {


    /**
     *
     * @param sqlQuery
     * @return
     */
    public List<String> extractFieldNamesFromSqlQuery(String sqlQuery) throws SQLException {
        if(sqlQuery == null){
            throw new IllegalArgumentException("sqlQuery parameter is null");
        }

        // SELECT ... FROM
        Pattern selectToFromPattern = Pattern.compile(" *SELECT *.+ FROM", Pattern.CASE_INSENSITIVE);
        Matcher selectToFromMatcher = selectToFromPattern.matcher(sqlQuery.replaceAll("\n", " "));
        String selectToFromString = null;
        if(selectToFromMatcher.find()){
            selectToFromString = selectToFromMatcher.group();
        } else {
            throw new SQLException("SQL query is not respected");
        }

        // AS field
        List<String> sqlFieldNames = new ArrayList<>();
        Pattern asPattern = Pattern.compile(" AS +[A-Z0-9_]+", Pattern.CASE_INSENSITIVE);
        Matcher asMatcher = asPattern.matcher(selectToFromString);
        while(asMatcher.find()){
            sqlFieldNames.add(asMatcher.group().split(" ")[2]);
        }

        return sqlFieldNames;
    }

    /**
     * Fill the SQLMetaDataColumn list (gives a complete MetaData tuple) from a connection and a table name
     *
     * @param sourceConnection
     * @param sourceTableName
     * @return
     * @throws SQLException
     */
    public List<SQLMetaDataColumn> generateSQLMetaDataTupleFromTableName(Connection sourceConnection, String sourceTableName) throws SQLException {
        return generateSQLMetaDataTupleFromSqlQuery(sourceConnection, "SELECT * FROM " + sourceTableName);
    }


    /**
     * Fill the SQLMetaDataColumn list (gives a complete MetaData tuple) from a connection and a SQL Query
     *
     * @param sourceConnection
     * @param sqlQuery
     * @return
     * @throws SQLException
     */
    public List<SQLMetaDataColumn> generateSQLMetaDataTupleFromSqlQuery(Connection sourceConnection, String sqlQuery) throws SQLException {
        List<SQLMetaDataColumn> list = new ArrayList<SQLMetaDataColumn>();

        try(
            Statement stmt = sourceConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery)
        ) {
            ResultSetMetaData rmd = rs.getMetaData();

            int nbColumn = rmd.getColumnCount(); // Number of columns in table
            for (int i = 0; i < nbColumn; i++) {
                SQLMetaDataColumn smdt = new SQLMetaDataColumn();
                smdt.setColumnName(rmd.getColumnName(i + 1));
                smdt.setJavaTypeId(rmd.getColumnType(i + 1));
                // Test line : System.out.println(rmd.getColumnType(i+1) + " " + rmd.getColumnTypeName(i+1));
                list.add(smdt);
            }

        }

        return list;
    }



        /**
         * Fill the SQLMetaDataColumn list (gives a complete MetaData tuple) from a Class<T>
         *
         * @param clazz
         * @return
         */
    public List<SQLMetaDataColumn> generateSQLMetaDataTupleFromClass(Class<?> clazz) {
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
