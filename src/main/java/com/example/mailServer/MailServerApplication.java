package com.example.mailServer;

import com.example.mailServer.Iterator.MapIterator;
import com.example.mailServer.Modules.Mail;
import com.example.mailServer.control.Control;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MailServerApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MailServerApplication.class, args);
//		Map<Integer,String> map=new HashMap<>();
//		map.put(1,"mohamed");
//		map.put(2,"ahmed");
//		map.put(3,"aly");
//		MapIterator<Integer,String> iterator=new MapIterator<>(map);
//		while (iterator.hasNext())
//		{  Map.Entry<Integer, String> entry = iterator.next();
//			System.out.println(entry.getKey()+" "+entry.getValue());
//		}
//		String []s={"Home Exercise 7.1.pdf"};
//		Mail mail=new Mail();
//		mail.setTo("ahmed@mail.com");
//		mail.setAttachment(s);
//		mail.setFrom("mohamed@mail.com");
//		mail.setBody("njrvnirbvirb");
//		mail.setDate("Sun Jan 01 21:30:55 EET 2023");
//		mail.setSubject("gyguutire");
//		mail.setPriority(2);
//		Mail[]array={mail};
//		Control c=new Control();
//		c.delete_mail("inbox","ahmed@mail.com",array);
				Control c=new Control();
				c.move_all("ahmed@mail.com","sent","inbox");

	}



}
