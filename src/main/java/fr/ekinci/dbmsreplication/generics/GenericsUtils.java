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

    public static Field getDeclaredField(String name, Class<?> type){
        for(Field f : getAllDeclaredFields(new ArrayList<>(), type)){
            if(f.getName().equals(name)){
                return f;
            }
        }

        throw new RuntimeException("This fieldname: " + name + "has not been found");
    }
}
