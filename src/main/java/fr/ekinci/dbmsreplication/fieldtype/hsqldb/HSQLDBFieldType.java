package fr.ekinci.dbmsreplication.fieldtype.hsqldb;

import fr.ekinci.dbmsreplication.SQLMetaDataColumn;
import fr.ekinci.dbmsreplication.fieldtype.FieldType;
import fr.ekinci.dbmsreplication.fieldtype.GenericDBFieldType;

/**
 * This class represents generic types from Java Metadata
 * to HSQLDB field type
 *
 * @author Gokan EKINCI
 */
public class HSQLDBFieldType extends FieldType {

    @Override
    public String fieldTypeName(SQLMetaDataColumn smdt) {
        return fieldTypeName(genericFieldType(smdt));
    }

    @Override
    public String fieldTypeName(Class<?> clazz) {
        return fieldTypeName(genericFieldType(clazz));
    }

    @Override
    public String fieldTypeName(GenericDBFieldType type) {
        switch(type){
            case TEXT : return "VARCHAR(4000)";
            case INTEGER : return "INTEGER";
            case BIGINT : return "BIGINT";
            case DECIMAL : return "DECIMAL";
            case DATE : return "TIMESTAMP";
            default : throw new RuntimeException("Unsupported GenericDBFieldType has been use: " + type);
        }
    }

}