package com.strider.contact.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.strider.contact.model.Contact;
import com.strider.contact.service.ContactService;
import com.strider.contact.util.Alert;

@Controller
public class ContactController {
	@Autowired
	private ContactService contactService;

	@GetMapping(value = "/contact")
	public String getContacts(Model model, HttpSession session) {
		List<Contact> contacts = new ArrayList<>();
		try {
			contactService.findAll().forEach(contacts::add);
			model.addAttribute("title", "Contact Manager");
			model.addAttribute("contacts", contacts);
		} catch (Exception e) {
			session.setAttribute("alert", Alert.builder().type("danger").message("Error fetching contacts").build());
			model.addAttribute("contacts", contacts);
		}

		return "contact";
	}

	@GetMapping(value = "/delete-contact/{id}")
	public String deleteContact(@PathVariable("id") Long contactId, Model model, HttpSession session) {
		try {
			contactService.deactivate(contactId);
		} catch (Exception e) {
			session.setAttribute("alert", Alert.builder().type("danger").message("Failed to deactivate contact").build());
		}
		return "redirect:/contact";
	}

	@GetMapping(value = "/update-contact/{id}")
	public String updateContact(@PathVariable("id") Long contactId, Model model) {
		Contact contact = contactService.getContact(contactId);

		model.addAttribute("title", "Contact Manager - Update");
		model.addAttribute("contact", contact);

		return "contact_update";
	}

	@PostMapping(value = "/update-contact")
	public String processUpdateContact(@ModelAttribute Contact contact, Model model, HttpSession session) {

		/** old contact details */
		Contact oldContact = contactService.getContact(contact.getId());

		try {
			Contact updatedContact = contactService.updateContact(contact);
			session.setAttribute("alert",
					Alert.builder().type("success").message("Contact updated successfully").build());
			model.addAttribute("contact", updatedContact);
		} catch (Exception e) {
			session.setAttribute("alert", Alert.builder().type("danger").message("Failed to add contact").build());
			model.addAttribute("contact", oldContact);
		}

		return "contact_update";
	}

//	@GetMapping(value = "/contact/{id}")
//	public String showContact(@PathVariable("id") Long contactId, Model model) {
//		Contact contact = contactService.getContact(contactId);
//
//		model.addAttribute("contact", contact);
//
//		return "show_user_contact_details";
//	}

	@GetMapping(value = "/add-contact")
	public String addContact(Model model) {
		model.addAttribute("title", "Contact Manager - Add");
		model.addAttribute("contact", Contact.builder().build());

		return "contact_add";
	}

	@PostMapping(value = "/add-contact")
	public String processAddContact(@ModelAttribute Contact contact, Model model, HttpSession session) {
		try {
			Contact newContact = contactService.addContact(contact);
			session.setAttribute("alert",
					Alert.builder().type("success").message("Contact added successfully").build());
			model.addAttribute("contact", Contact.builder().build());
		} catch (Exception e) {
			session.setAttribute("alert", Alert.builder().type("danger").message("Failed to add contact").build());
			model.addAttribute("contact", contact);
		}

		return "contact_add";
	}
}
