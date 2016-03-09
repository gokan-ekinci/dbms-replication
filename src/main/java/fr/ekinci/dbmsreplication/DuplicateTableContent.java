package fr.ekinci.dbmsreplication;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import fr.ekinci.dbmsreplication.fieldtype.FieldType;
import fr.ekinci.dbmsreplication.generics.GenericsUtils;

/**
 * <p>A class that replicate a table content to another existing table</p>
 *
 * @author Gokan EKINCI
 */
public class DuplicateTableContent extends AbstractDuplicateTable {
    private final static Logger LOG = Logger.getLogger(DuplicateTableContent.class.getName());

    public void sqlDuplicateContent(
            Connection sourceConnection, // ex : MainframeCpuConnection.getInstance();
            Connection destinationConnection, // ex : MainframeCpuConnection.getInstance();
            FieldType destinationFieldType,
            String sourceTableName,
            String destinationTableName
    ) throws SQLException {

        List<SQLMetaDataColumn> list = generateSQLMetaDataTupleFromTableName(sourceConnection, sourceTableName);
        Statement stmtSource = sourceConnection.createStatement();
        ResultSet rsSource = stmtSource.executeQuery("SELECT * FROM " + sourceTableName);

        int numberOfColumns = list.size();
        String valuesPlaceholders = placeholders(numberOfColumns);
        PreparedStatement psDestination = destinationConnection.prepareStatement("INSERT INTO " + destinationTableName + " VALUES"+ valuesPlaceholders);

        while(rsSource.next()){
            for(int i = 1; i <= numberOfColumns; i++){
                psDestination.setObject(i, rsSource.getObject(i));
            }
            psDestination.executeUpdate();
        }

        rsSource.close();
        stmtSource.close();
        psDestination.close();
    }




    public void sqlDuplicateContent(
            Connection sourceConnection, // ex : MainframeCpuConnection.getInstance();
            Connection destinationConnection, // ex : MainframeCpuConnection.getInstance();
            FieldType destinationFieldType,
            String sourceTableName,
            String destinationTableName,
            int maxNbTuplesPerInsertion
    ) throws SQLException {

        // Init Source
        Statement stmtSource = sourceConnection.createStatement();
        ResultSet rsSource = stmtSource.executeQuery("SELECT * FROM " + sourceTableName);

        // Const variables
        final List<SQLMetaDataColumn> list = generateSQLMetaDataTupleFromTableName(sourceConnection, sourceTableName);
        final int numberOfColumns = list.size();
        final String insertIntoTableNameValues = "INSERT INTO " + destinationTableName + " VALUES";

        // Prepare for maximum tuples for Destination
        String maxPlaceholders = multiplePlaceholders(maxNbTuplesPerInsertion, numberOfColumns);
        PreparedStatement psDestinationMaximum = destinationConnection.prepareStatement(insertIntoTableNameValues + maxPlaceholders);

        // Dynamic variables
        List<Object[]> tempTuples = new ArrayList<Object[]>();
        int currentTupleNumber = 0;

        while(rsSource.next()){
            Object[] tuple = new Object[numberOfColumns];
            for(int i = 0; i < numberOfColumns; i++){
                tuple[i] = rsSource.getObject(i + 1);
            }
            tempTuples.add(tuple);

            // Increment and compare
            if(++currentTupleNumber == maxNbTuplesPerInsertion){
                fillPreparedStatement(psDestinationMaximum, tempTuples);
                psDestinationMaximum.executeUpdate(); // Send block of data to destination
                tempTuples.clear();                   // Re-init tempTuples
                currentTupleNumber = 0;               // Re-init counter
            }
        }

        // if tuples remain in tempTuples (see it with currentTupleNumber), then flush it !
        if(currentTupleNumber > 0){
            PreparedStatement pstmt = destinationConnection.prepareStatement(
                    insertIntoTableNameValues + multiplePlaceholders(currentTupleNumber, numberOfColumns)
            );
            fillPreparedStatement(pstmt, tempTuples);
            pstmt.executeUpdate();
            pstmt.close();
        }

        rsSource.close();
        stmtSource.close();
        psDestinationMaximum.close();
    }

    /**
     * From Java collection to table
     * @param sourceToReplicate
     * @param metaColumns
     * @param destinationConnection
     * @param destinationTableName
     * @param maxNbTuplesPerInsertion
     */
    public void sqlDuplicateContent(
        Collection<?> sourceToReplicate,
        List<SQLMetaDataColumn> metaColumns,
        Connection destinationConnection,
        String destinationTableName,
        int maxNbTuplesPerInsertion
    ) throws SQLException, NoSuchFieldException, IllegalAccessException {

        // Const variables
        final int numberOfColumns = metaColumns.size();
        final String insertIntoTableNameValues = "INSERT INTO " + destinationTableName + " VALUES";

        // Prepare for maximum tuples for Destination
        String maxPlaceholders = multiplePlaceholders(maxNbTuplesPerInsertion, numberOfColumns);
        PreparedStatement psDestinationMaximum = destinationConnection.prepareStatement(insertIntoTableNameValues + maxPlaceholders);

        // Dynamic variables
        List<Object[]> tempTuples = new ArrayList<Object[]>();
        int currentTupleNumber = 0;

        for(Object objectTuple : sourceToReplicate){
            Object[] tuple = new Object[numberOfColumns];
            Class<?> objectTupleClass = objectTuple.getClass();

            for(int i = 0; i < numberOfColumns; i++){
                Field field = GenericsUtils.getField(metaColumns.get(i).getColumnName(), objectTupleClass);
                tuple[i] = field.get(objectTuple);
            }
            tempTuples.add(tuple);

            // Increment and compare
            if(++currentTupleNumber == maxNbTuplesPerInsertion){
                fillPreparedStatement(psDestinationMaximum, tempTuples);
                psDestinationMaximum.executeUpdate(); // Send block of data to destination
                tempTuples.clear();                   // Re-init tempTuples
                currentTupleNumber = 0;               // Re-init counter
            }
        }

        // if tuples remain in tempTuples (see it with currentTupleNumber), then flush it !
        if(currentTupleNumber > 0){
            PreparedStatement pstmt = destinationConnection.prepareStatement(
                    insertIntoTableNameValues + multiplePlaceholders(currentTupleNumber, numberOfColumns)
            );
            fillPreparedStatement(pstmt, tempTuples);
            pstmt.executeUpdate();
            pstmt.close();
        }

        psDestinationMaximum.close();
    }


    public void fillPreparedStatement(PreparedStatement pstmt, List<Object[]> tuples)
            throws SQLException {
        int indexCounter = 0;
        for(Object[] tuple : tuples){
            for(Object columnValue: tuple){
                pstmt.setObject(++indexCounter, columnValue);
            }
        }
    }

    /**
     * Return multiple placeholders for VALUES, ex: (?,?,?,?),(?,?,?,?),(?,?,?,?)
     * @param numberOfTuples
     * @param numberOfColumns
     * @return
     */
    private String multiplePlaceholders(int numberOfTuples, int numberOfColumns){
        String initialPlaceholders = placeholders(numberOfColumns) + ",";
        StringBuilder placeholdersSB = new StringBuilder();
        for(int i = 0; i < numberOfTuples; i++){
            placeholdersSB.append(initialPlaceholders);
        }
        placeholdersSB.setLength(placeholdersSB.length() - 1); // Remove last char ","
        return placeholdersSB.toString();
    }

    /**
     * Return placeholders for VALUES, ex: (?,?,?,?)
     * @param numberOfColumns
     * @return
     */
    private String placeholders(int numberOfColumns){
        String chain = "(";
        for(int i = 0; i < numberOfColumns; i++){
            chain += "?,";
        }
        // Remove last character ","
        chain = chain.substring(0, chain.length() - 1);
        chain += ")";

        return chain;
    }
}
