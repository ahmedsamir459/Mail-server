package com.example.mailServer;
import com.example.mailServer.Modules.result;
import com.example.mailServer.control.Control;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
@SpringBootTest
public class FolderTest {
    @Test
    public void testaddFolder() throws Exception {
        Control test = new Control();
        assertEquals("File already exists.", test.addfolder("ahmed@mail.com","important"));
        assertEquals("File already exists.", test.addfolder("ahmed@mail.com","index"));
    }
    @Test
    public void testrenameFolder() throws Exception {
        Control test = new Control();
        assertEquals("File renamed: mohm.json", test.renamefolder("ahmed@mail.com","trash","mohm"));
        assertEquals("File not found.", test.renamefolder("ahmed@mail.com","mohamed","mohm"));
    }
    @Test
    public void testdeleteFolder() throws Exception {
        Control test = new Control();
        assertEquals(new result("File deleted: index.json", true),test.deletefolder("ahmed@mail.com", "index"));
        assertEquals(new result("File not found.", false), test.deletefolder("ahmed@mail.com", "mohamed"));
    }


}
