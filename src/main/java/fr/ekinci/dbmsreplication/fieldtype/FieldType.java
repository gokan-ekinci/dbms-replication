package fr.ekinci.dbmsreplication.fieldtype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Types;
import java.util.Date;

import fr.ekinci.dbmsreplication.SQLMetaDataColumn;


/**
 * <p>This class represents generic types from Java Metadata
 * to the corresponding DBMS type</p>
 * See: http://cr.openjdk.java.net/~lancea/8006505/webrev.00/src/share/classes/java/sql/Types.java-.html
 *
 * @author Gokan EKINCI
 */
public abstract class FieldType {

    /**
     * Convert Java Meta
     * @param smdt
     * @return
     */
    public static GenericDBFieldType genericFieldType(SQLMetaDataColumn smdt){
        final int JAVA_TYPE_ID = smdt.getJavaTypeId();

        // TEXT
        if(JAVA_TYPE_ID == Types.CHAR
                || JAVA_TYPE_ID == Types.VARCHAR
                || JAVA_TYPE_ID == Types.NCHAR
                || JAVA_TYPE_ID == Types.NVARCHAR
                ){
            return GenericDBFieldType.TEXT;
        }

        // FLOAT/DECIMAL
        else if (JAVA_TYPE_ID == Types.FLOAT
                || JAVA_TYPE_ID == Types.REAL
                || JAVA_TYPE_ID == Types.DOUBLE
                || JAVA_TYPE_ID == Types.DECIMAL
                || JAVA_TYPE_ID == Types.NUMERIC
                ){
            return GenericDBFieldType.DECIMAL;
        }

        // INTEGER
        else if(JAVA_TYPE_ID == Types.TINYINT
                || JAVA_TYPE_ID == Types.SMALLINT
                || JAVA_TYPE_ID == Types.INTEGER
                ){
            return GenericDBFieldType.INTEGER;
        }

        // BIG INTEGER
        else if(JAVA_TYPE_ID == Types.BIGINT){
            return GenericDBFieldType.BIGINT;
        }

        // DATE
        else if(JAVA_TYPE_ID == Types.DATE
                || JAVA_TYPE_ID == Types.TIMESTAMP){
            return GenericDBFieldType.DATE;
        }

        throw new RuntimeException("genericFieldType(SQLMetaDataColumn) : The following JAVA_TYPE_ID is not supported: " + JAVA_TYPE_ID);
    }


    /**
     *
     * @param clazz
     * @return
     */
    public static GenericDBFieldType genericFieldType(Class<?> clazz){

        // TEXT
        if(clazz == String.class){
            return GenericDBFieldType.TEXT;
        }

        // FLOAT/DECIMAL
        else if (clazz == double.class || clazz == float.class || clazz == Double.class || clazz == Float.class || clazz == BigDecimal.class){
            return GenericDBFieldType.DECIMAL;
        }

        // INTEGER
        else if(clazz == int.class || clazz == long.class || clazz == short.class || clazz == Integer.class || clazz == Long.class || clazz == Short.class){
            return GenericDBFieldType.INTEGER;
        }

        // BIG INTEGER
        else if(clazz == BigInteger.class){
            return GenericDBFieldType.BIGINT;
        }

        // DATE
        else if(clazz == Date.class){
            return GenericDBFieldType.DATE;
        }

        throw new RuntimeException("genericFieldType(Class<?>) : The following class: " + clazz.getName() + " is not supported");
    }





    /**
     * <p>Get DBMS real field name from SQL (SQLMetaDataColumn) </p>
     * <p>Tips for Types : http://www.docjar.com/html/api/java/sql/Types.java.html</p>
     * @return
     */
    public abstract String fieldTypeName(SQLMetaDataColumn smdt);

    /**
     * Get DBMS real field name from Java
     */
    public abstract String fieldTypeName(Class<?> clazz);

    /**
     * Get DBMS real field name from GenericDBFIeldType
     */
    public abstract String fieldTypeName(GenericDBFieldType type);

}