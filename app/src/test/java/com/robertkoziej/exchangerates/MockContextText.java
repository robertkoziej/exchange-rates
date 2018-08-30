package com.robertkoziej.exchangerates;

import android.content.Context;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class MockContextText {
    @Test
    public void mockContext() {
        Context contextMocked = Mockito.mock(Context.class);
        Assert.assertThat(contextMocked, is(notNullValue()));
    }
}
