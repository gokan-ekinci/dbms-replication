package fr.ekinci.dbmsreplication.inmemorysql;

import fr.ekinci.dbmsreplication.DuplicateTableContent;
import fr.ekinci.dbmsreplication.DuplicateTableStructure;
import fr.ekinci.dbmsreplication.SQLMetaDataColumn;
import fr.ekinci.dbmsreplication.fieldtype.FieldType;
import fr.ekinci.dbmsreplication.fieldtype.hsqldb.HSQLDBFieldType;
import fr.ekinci.dbmsreplication.generics.GenericsUtils;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 *
 *
 Constraints for ReturnType :
 * Use wrapper type for ReturnType because primitive types do not handle null in Java
 * DECIMAL field in SQL means BigDecimal in Java, don't use double/Double
 * INT field in SQL means Integer in Java, don't use Long
 * Do not use sql-reserved keywords for fields, and escaping with "`" or preceding keyword with "_" may not work
 *
 * @author Gokan EKINCI
 */
public class InMemorySQL {

    private final static Logger LOG = Logger.getLogger(InMemorySQL.class.getName());
    private final static int NUMBER_OF_TUPLES = 1_000;

    private int nbElements;
    private List<Class<?>> classes = new ArrayList<>();
    private List<Collection<?>> lists = new ArrayList<>();


    public <T> InMemorySQL add(Class<T> clazz, Collection<T> list){
        this.nbElements++;
        this.classes.add(clazz);
        this.lists.add(list);
        return this;
    }

    /**
     *
     *
     * @param returnType
     * @param sqlQuery table names must be t1, t2, tn etc
     * @param parameters
     * @param <T>
     * @return
     * @throws SQLException
     */
    public <T> List<T> executeQuery(Class<T> returnType, String sqlQuery, Object... parameters)
            throws SQLException {
        return executeQuery("d" + UUID.randomUUID().toString().replace("-", ""), returnType, sqlQuery, parameters);
    }

    /**
     *
     *
     * @param randDbName
     * @param returnType
     * @param sqlQuery table names must be t1, t2, tn etc
     * @param parameters
     * @param <T>
     * @return
     * @throws SQLException
     */
    public <T> List<T> executeQuery(String randDbName, Class<T> returnType, String sqlQuery, Object... parameters)
            throws SQLException {
        try {
            LOG.info("Your SQL query is : " + sqlQuery);
            LOG.info("+++ executeQuery() for " + randDbName + " has started +++");
            LOG.info("    WAIT FOR TREATMENT...");
            Instant start = Instant.now();
            // String dbName = "d" + Thread.currentThread().getId(); => pffff don't use this


            try(Connection con = DriverManager.getConnection("jdbc:hsqldb:mem:" + randDbName, "SA", "")){
                con.setAutoCommit(false);

                // Util objects
                DuplicateTableStructure dt = new DuplicateTableStructure();
                List<String> fieldNames = dt.extractFieldNamesFromSqlQuery(sqlQuery);
                DuplicateTableContent dc = new DuplicateTableContent();
                FieldType destinationFieldType = new HSQLDBFieldType();

                // Step 1 : Fill `listOfColumns`
                // Step 2 : Create table
                // Step 3 : Fill table
                for(int i = 0; i < nbElements; i++) {
                    // Step 1 : Fill `listOfColumns`
                    List<SQLMetaDataColumn> columns = dt.generateSQLMetaDataTupleFromClass(classes.get(i));

                    // Step 2 : Create table
                    String tableName = "t"+(i+1);
                    String createTable = dt.generateSQLTableFromJavaClass(destinationFieldType, tableName, columns);
                    LOG.info("In database : " + randDbName + " => " + createTable);
                    try(PreparedStatement pstmt = con.prepareStatement(createTable)){
                        pstmt.executeUpdate();
                    }

                    // Step 3 : Fill table
                    dc.sqlDuplicateContent(lists.get(i), columns, con, tableName, NUMBER_OF_TUPLES);
                }


                // EXECUTE REQUEST
                List<T> result = new ArrayList<>();
                // List<SQLMetaDataColumn> resultColumns = dt.generateSQLMetaDataTupleFromSqlQuery(con, sqlQuery); => Need for having less restrictive columns

                try(PreparedStatement pstmt = con.prepareStatement(sqlQuery)){
                    for(int i = 0, max = parameters.length; i < max; i++){
                        pstmt.setObject(i, parameters[i]);
                    }

                    try(ResultSet rs = pstmt.executeQuery()){
                        while(rs.next()){
                            T entry = returnType.newInstance();

                            for(String fieldName : fieldNames){
                                GenericsUtils.setField(entry, rs.getObject(fieldName), fieldName, returnType);
                            }


                            /*
                            for(Field field : GenericsUtils.getAllDeclaredFields(new ArrayList<>(), returnType)) {
                                field.setAccessible(true);
                                // Debug (use this commentary with precaution) : LOG.info("Test => field.getName(): " + field.getName() + " rs.getObject(field.getName()): " +  rs.getObject(field.getName()));
                                field.set(entry, rs.getObject(field.getName()));
                            }
                            */

                            result.add(entry);
                        }
                    }
                }


                // DROP DATABASE (Release memory)
                // Documentation : http://www.hsqldb.org/doc/guide/ch09.html#N1206A
                try(PreparedStatement pstmt = con.prepareStatement("DROP SCHEMA PUBLIC CASCADE")){
                    pstmt.executeUpdate();
                }


                LOG.info("--- executeQuery() for " + randDbName + " has finished in : " + Duration.between(start, Instant.now()).getSeconds() + " seconds ---");
                return result;
            }
        } catch(SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException e){
            throw new SQLException(e);
        }
    }

}
