package fr.ekinci.dbmsreplication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import fr.ekinci.dbmsreplication.fieldtype.FieldType;


/**
 * A class that generates a table structure from another existing table
 *
 * @author Gokan EKINCI
 */
public class DuplicateTableStructure extends AbstractDuplicateTable {

    public String sqlTable(
            Connection sourceConnection, // ex : MainframeCpuConnection.getInstance();
            FieldType destinationFieldType,
            String sourceTableName,
            String destinationTableName
    ) throws SQLException {
        return generateSQLTableFromSqlJavaId(
                destinationFieldType,
                destinationTableName,
                generateSQLMetaDataTuple(
                        sourceConnection,
                        sourceTableName
                )
        );
    }

    /**
     * <p>Ex:
     * CREATE TABLE toto (
     *     field1 INTEGER,
     *     field2 DECIMAL(15, 2)
     * );</p>
     *
     * @param destinationFieldType
     * @param destinationTableName
     * @param listOfColumns
     * @return
     */
    public String generateSQLTableFromSqlJavaId(FieldType destinationFieldType, String destinationTableName, List<SQLMetaDataColumn> listOfColumns){
        String sqlTable = "CREATE TABLE " + destinationTableName + "(\n";
        for(SQLMetaDataColumn smdt : listOfColumns){
            sqlTable += "    " + smdt.getColumnName() + " " + destinationFieldType.fieldTypeName(smdt) + ",\n";
        }

        // Erase last two characters
        if (sqlTable.length() > 2) {
            sqlTable = sqlTable.substring(0, sqlTable.length() - 2);
        }

        sqlTable += "\n);\n";

        return sqlTable;
    }

    /**
     * <p>Ex:
     * CREATE TABLE toto (
     *     field1 INTEGER,
     *     field2 DECIMAL(15, 2)
     * );</p>
     *
     * @param destinationFieldType
     * @param destinationTableName
     * @param listOfColumns
     * @return
     */
    public String generateSQLTableFromJavaClass(FieldType destinationFieldType, String destinationTableName, List<SQLMetaDataColumn> listOfColumns){
        String sqlTable = "CREATE TABLE " + destinationTableName + "(\n";
        for(SQLMetaDataColumn smdt : listOfColumns){
            sqlTable += "    " + smdt.getColumnName() + " " + destinationFieldType.fieldTypeName(smdt.getGenericDBFieldType()) + ",\n";
        }

        // Erase last two characters
        if (sqlTable.length() > 2) {
            sqlTable = sqlTable.substring(0, sqlTable.length() - 2);
        }

        sqlTable += "\n);\n";

        return sqlTable;
    }

}