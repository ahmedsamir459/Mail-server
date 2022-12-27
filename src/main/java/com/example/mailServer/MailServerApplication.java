package com.example.mailServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MailServerApplication {

	public static void main(String[] args) throws NullPointerException, IOException {
		SpringApplication.run(MailServerApplication.class, args);

	}



}
