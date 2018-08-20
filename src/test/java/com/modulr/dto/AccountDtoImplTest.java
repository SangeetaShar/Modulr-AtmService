package com.modulr.dto;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by Sangeeta Sharma on 16/08/2018.
 */
public class AccountDtoImplTest extends ParentDtoTest {

    private AccountDto accountDto = new AccountDtoImpl("someCcNumber", new BigDecimal(200L));

    @Test
    public void testAllGetters() throws InvocationTargetException, IllegalAccessException {
        Method methods[] = AccountDto.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equalsIgnoreCase("getClass")) {
                method.invoke(accountDto);
            }
        }
    }

    @Test
    public void testAccountDtoSetter() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        testAllSetters(accountDto);
    }

}

