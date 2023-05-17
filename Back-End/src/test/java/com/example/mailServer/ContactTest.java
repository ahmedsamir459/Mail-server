package com.example.mailServer;

import com.example.mailServer.Modules.result;
import com.example.mailServer.control.Control;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ContactTest {
    @Mock
    private Control control;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testaddcontact() throws Exception {
        when(control.addcontact("ahmed@mail.com", "wael",new String[]{"wael@mail.com"})).thenReturn(new result("Done",false));
        when(control.addcontact("ahmed@mail.com", "yosef",new String[]{"yosef@mail.com"})).thenReturn(new result("error",true));
        assertEquals(new result("contact renamed",false), control.addcontact("ahmed@mail.com", "wael", new String[]{"wael@mail.com"}));
        assertEquals(new result("error",true), control.addcontact("ahmed@mail.com", "yosef", new String[]{"yosef@mail.com"}));
    }
    @Test
    public void testdeletecontact() throws Exception {
        when(control.deletecontact("ahmed@mail.com","mohamed@mail.com")).thenReturn(new result("Done",false));
        when(control.deletecontact("ahmed@mail.com","elzainy@mail.com")).thenReturn(new result("error",true));
        assertEquals(new result("error",false), control.deletecontact("ahmed@mail.com","elzainy@mail.com"));
        assertEquals(new result("error",true), control.deletecontact("ahmed@mail.com", "mohamed@mail.com"));
    }
    @Test
    public void testrenamecontact() throws Exception {
//        when(control.rename_contact("ahmed@mail.com", "mohamed","ahmed")).thenReturn("Done");
//        when(control.rename_contact("ahmed@mail.com", "wael","elzainy")).thenReturn("error");
    }
}
