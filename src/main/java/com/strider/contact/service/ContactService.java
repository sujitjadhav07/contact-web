package com.strider.contact.service;

import java.util.List;

import com.strider.contact.model.Contact;

public interface ContactService {
	public List<Contact> findAll();
	public void deactivate(Long contactId);
	public Contact getContact(Long contactId);
	public Contact updateContact(Contact contact);
	public Contact addContact(Contact contact);
}
