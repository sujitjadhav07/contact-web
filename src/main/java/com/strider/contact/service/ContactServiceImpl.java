package com.strider.contact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.strider.contact.model.Contact;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Value("${contact.api.url}")
	private String apiUrl;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Contact> findAll() {
		ResponseEntity<List<Contact>> response =
		        restTemplate.exchange(apiUrl + "contacts",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Contact>>() {
		            });
		List<Contact> contacts = response.getBody();
		return contacts;
	}

	@Override
	public void deactivate(Long contactId) {
		restTemplate.delete(apiUrl + "contacts/{id}", contactId);
	}

	@Override
	public Contact getContact(Long contactId) {
		Contact contact = restTemplate.getForObject(apiUrl+ "contacts/{id}", Contact.class, contactId);

		return contact;
	}

	@Override
	public Contact updateContact(Contact contact) {
		restTemplate.put(apiUrl + "contacts/{id}" , contact, contact.getId());
		return contact;
	}

	@Override
	public Contact addContact(Contact contact) {
		Contact newContact = restTemplate.postForObject(apiUrl + "contacts", contact, Contact.class);
		return newContact;
	}

}
