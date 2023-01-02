package com.example.mailServer;
import com.example.mailServer.Modules.Mail;
import com.example.mailServer.Modules.result;
import com.example.mailServer.control.Control;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;

@SpringBootTest
public class MailingTest {

    @Test
    public void testsendMail() throws Exception {
        Control test = new Control();
        assertEquals(new result("saved",true), test.save_mail(new Mail("ahmed@mail,com","mado@mail.com","hello","hello world")));
        assertEquals(new result("error",false), test.save_mail(new Mail()));
    }
    @Test
    public void testdeleteMail() throws Exception {
        Control test = new Control();
        assertEquals(new result("deleted", true), test.delete_mail("inbox", "ahmed@mail.com", new Mail[]{new Mail("ahmed@mail,com", "mado@mail.com", "hello", "hello world")}));
        assertEquals(new result("error", false), test.delete_mail("inbox", "ahmed@mail.com", new Mail[]{new Mail()}));
    }
    @Test
    public void testmoveMail() throws Exception {
        Control test = new Control();
        assertEquals("moved", test.move_mail("inbox", "trash", "ahmed@mail.com", new Mail[]{new Mail("ahmed@mail,com", "mado@mail.com", "hello", "hello world")}));
        assertEquals("error", test.move_mail("inbox", "trash", "ahmed@mail.com", new Mail[]{new Mail()}));
    }
}
