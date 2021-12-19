package com.strider.contact.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strider.contact.model.Contact;
import com.strider.contact.service.ContactService;

@WebMvcTest(ContactController.class)
class ContactControllerTest {

	@MockBean
	ContactService contactService;

	@Autowired
	MockMvc mockMvc;

	private Contact contact;

	private List<Contact> contacts;

	@BeforeEach
	void setUp() throws Exception {
		contact = Contact.builder().id(1L).firstName("Tom").lastName("Cook").email("tomcook@abc.com")
				.phoneNumber("43245778").active(true).build();

		contacts = new ArrayList<>();
		contacts.add(contact);
		contacts.add(Contact.builder().id(2L).firstName("Alex").lastName("Smith").email("alexsmith@bb.com")
				.phoneNumber("78082920").active(true).build());
	}

	@Test
	void testGetContacts() throws Exception {
		when(contactService.findAll()).thenReturn(contacts);

		mockMvc.perform(get("/contact")).andExpect(status().isOk()).andExpect(model().attributeExists("contacts"))
				.andExpect(model().attributeExists("title")).andExpect(view().name("contact"));
	}

	@Test
	void testDeleteContact() throws Exception {
		doNothing().when(contactService).deactivate(anyLong());

		mockMvc.perform(get("/delete-contact/{id}", "1")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/contact"));
	}

	@Test
	void testUpdateContact() throws Exception {
		when(contactService.getContact(anyLong())).thenReturn(contact);

		mockMvc.perform(get("/update-contact/{id}", "1")).andExpect(status().isOk())
				.andExpect(model().attributeExists("contact")).andExpect(model().attributeExists("title"))
				.andExpect(view().name("contact_update"));

	}

	@Test
	void testAddContact() throws Exception {
		mockMvc.perform(get("/add-contact")).andExpect(status().isOk()).andExpect(model().attributeExists("contact"))
				.andExpect(model().attributeExists("title")).andExpect(view().name("contact_add"));
	}

	@Test
	void testProcessUpdateContact() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		when(contactService.addContact(any())).thenReturn(contact);
		when(contactService.updateContact(any())).thenReturn(contact);

		mockMvc.perform(post("/update-contact/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(contact))).andExpect(status().isOk())
				.andExpect(model().attributeExists("contact")).andExpect(view().name("contact_update"));
	}

	
	@Test
	void testProcessAddContact() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		when(contactService.addContact(any())).thenReturn(contact);
		
		mockMvc.perform(post("/add-contact/").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(contact))).andExpect(status().isOk())
				.andExpect(model().attributeExists("contact")).andExpect(view().name("contact_add"));
	}
}
