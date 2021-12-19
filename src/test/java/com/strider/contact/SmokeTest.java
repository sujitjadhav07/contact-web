package com.strider.contact;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.strider.contact.controller.ContactController;

@SpringBootTest
public class SmokeTest {

	@Autowired
	private ContactController contactController;

	@Test
	void contextLoads() {
		assertNotNull(contactController);
	}
}
