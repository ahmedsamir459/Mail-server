package com.example.mailServer;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.mailServer.control.Control;

@SpringBootTest
public class Signtest {
    @Mock
    private Control control;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testsignin() throws Exception {
        when(control.signin("ahmed@mail.com","123456789")).thenReturn("Done");
        assertEquals("Done",control.signin("ahmed@mail.com","123456789"));
        assertEquals(null,control.signin("ahmed@mail.com","123456789"));
        verify(control).signin("ahmed@mail.com","123456789");
    }
    @Test
    public void testsignup() throws Exception {
        when(control.signup("mohamed@mail.com", "123456789")).thenReturn("Done");
        assertEquals("Done", control.signup("mohamed@mail.com", "123456789"));
        assertEquals(null, control.signup("   ", "123456789"));
        verify(control).signup("mohamed@mail.com", "123456789");
    }


}
