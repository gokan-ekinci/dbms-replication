package fr.ekinci.dbmsreplication.generics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gekinci on 08/03/16.
 *
 * @author Gokan EKINCI
 */
public class GenericsUtils {

    /**
     * Get all declared fields from Class<?>
     * Note : getDeclaredFields() may return private attributes, but does not return Parents fields
     *
     * @param fields
     * @param type
     * @return
     */
    public static List<Field> getAllDeclaredFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            fields = getAllDeclaredFields(fields, type.getSuperclass());
        }

        return fields;
    }

    /**
     * setAccessible(true) is done
     *
     * @param fieldName
     * @param type
     * @return
     */
    public static Field getField(String fieldName, Class<?> type){
        for(Field f : getAllDeclaredFields(new ArrayList<>(), type)){
            if(f.getName().equals(fieldName)){
                f.setAccessible(true);
                return f;
            }
        }

        throw new RuntimeException("This fieldname: " + fieldName + " has not been found");
    }

    /**
     * setAccessible(true) is done from {@link GenericsUtils#getField(String, Class)}
     *
     * @param instance
     * @param value
     * @param fieldName
     * @param type
     * @throws IllegalAccessException
     */
    public static void setField(Object instance, Object value, String fieldName, Class<?> type) throws IllegalAccessException {
        Field field = getField(fieldName, type);
        field.set(instance, value);
    }
}
