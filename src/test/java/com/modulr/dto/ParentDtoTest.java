package com.modulr.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParentDtoTest {

    public void testAllSetters(Object dto) throws InvocationTargetException, IllegalAccessException {
        Method methods[] = dto.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set") &&
                    method.getParameterCount() == 1 && !method.getParameterTypes()[0].isEnum()) {
                if (method.getParameterTypes()[0].isPrimitive()) {
                    setPrimitives(dto, method);
                } else if (method.getParameterTypes()[0].equals(Long.class)) {
                    method.invoke(dto, 0L);
                } else if (method.getParameterTypes()[0].equals(List.class)) {
                    Object obj = new ArrayList();
                    method.invoke(dto, obj);
                } else if (method.getParameterTypes()[0].equals(Map.class)) {
                    Object obj = new HashMap<>();
                    method.invoke(dto, obj);
                } else if (method.getParameterTypes()[0].equals(Integer.class)) {
                    method.invoke(dto, 1);
                } else if (method.getParameterTypes()[0].equals(LocalDateTime.class)) {
                    method.invoke(dto, LocalDateTime.now());
                } else if (method.getParameterTypes()[0].equals(BigDecimal.class)) {
                    method.invoke(dto, new BigDecimal(100L));
                } else if (method.getParameterTypes()[0].equals(String.class)) {
                    method.invoke(dto, "");
                }
            }
        }
    }

    private void setPrimitives(Object dto, Method method) throws IllegalAccessException, InvocationTargetException {
        if (method.getParameterTypes()[0].equals(Boolean.class) ||
                method.getParameterTypes()[0].equals(boolean.class)) {
            method.invoke(dto, true);
        } else if (method.getParameterTypes()[0].equals(Long.class)) {
            method.invoke(dto, 1L);
        } else if (method.getParameterTypes()[0].equals(int.class)) {
            method.invoke(dto, 1);
        } else {
            method.invoke(dto, 0);
        }
    }
}
