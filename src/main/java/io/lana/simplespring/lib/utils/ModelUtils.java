package io.lana.simplespring.lib.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public final class ModelUtils {
    private ModelUtils() {
    }

    public static <T> List<Object> getPropertiesValue(T object) {
        Class<?> clazz = object.getClass();
        try {
            List<Object> values = new ArrayList<>();
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors()) {
                values.add(propertyDescriptor.getReadMethod().invoke(object));
            }
            return values;
        } catch (Exception e) {
            throw new RuntimeException("Cannot get properties of type: " + clazz.getName(), e);
        }
    }

    public static <T> List<String> getPropertiesName(T object) {
        Class<?> clazz = object.getClass();
        try {
            List<String> names = new ArrayList<>();
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors()) {
                names.add(CaseUtils.toSnakeCase(propertyDescriptor.getName()));
            }
            return names;
        } catch (Exception e) {
            throw new RuntimeException("Cannot get properties of type: " + clazz.getName(), e);
        }
    }
}
