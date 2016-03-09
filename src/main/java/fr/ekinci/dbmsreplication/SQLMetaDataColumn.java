package fr.ekinci.dbmsreplication;

import fr.ekinci.dbmsreplication.fieldtype.GenericDBFieldType;

/**
 * <p>A class which represent a column name and corresponding Java type</p>
 *
 * @author Gokan EKINCI
 */
public class SQLMetaDataColumn {
    private String columnName;

    // When Java to SQL
    private GenericDBFieldType genericDBFieldType;

    // When SQL To Java
    private int javaTypeId;

    @Override
    public String toString() {
        return "SQLMetaDataColumn{" +
                "columnName='" + columnName + '\'' +
                ", genericDBFieldType=" + genericDBFieldType +
                ", javaTypeId=" + javaTypeId +
                '}';
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public GenericDBFieldType getGenericDBFieldType() {
        return genericDBFieldType;
    }

    public void setGenericDBFieldType(GenericDBFieldType genericDBFieldType) {
        this.genericDBFieldType = genericDBFieldType;
    }

    public int getJavaTypeId() {
        return javaTypeId;
    }

    public void setJavaTypeId(int javaTypeId) {
        this.javaTypeId = javaTypeId;
    }
}