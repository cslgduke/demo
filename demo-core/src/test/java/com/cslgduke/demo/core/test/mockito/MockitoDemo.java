package com.cslgduke.demo.core.test.mockito;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;



/**
 * @author i565244
 */
public class MockitoDemo {

    @Test
    public void testVerifyInteraction() {
        List mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.clear();
        verify(mockedList).add("one");
        verify(mockedList).clear();


    }

    @Test
    public void testStubCall() {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        assertThat(mockedList.get(0)).isEqualTo("first");
    }
}
