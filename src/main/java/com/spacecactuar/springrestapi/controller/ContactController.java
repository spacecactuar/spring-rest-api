package com.spacecactuar.springrestapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spacecactuar.springrestapi.entity.Contact;
import com.spacecactuar.springrestapi.exception.ResourceNotFoundException;
import com.spacecactuar.springrestapi.repository.ContactRepository;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;
	
	// GET Contacts
	@GetMapping
	public List<Contact> getAllContacts(){
		return this.contactRepository.findAll();
	}
	
	// GET Contact by ID
	@GetMapping("/{id}")
	public Contact getContactById(@PathVariable (value = "id") long contactId) {
		return this.contactRepository.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o ID :" + contactId));
	}
		
	// POST Contact
	@PostMapping
	public Contact createContact(@RequestBody Contact contact) {
		return this.contactRepository.save(contact);
	}
	
	// PUT Contact
	@PutMapping("/{id}")
	public Contact updateContact(@RequestBody Contact contact, @PathVariable ("id") long contactId) {
		Contact currentContact = this.contactRepository.findById(contactId)
			.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o ID :" + contactId));
		currentContact.setNome(contact.getNome());
		currentContact.setEmail(contact.getEmail());
		currentContact.setCpf(contact.getCpf());
		currentContact.setDataDeNascimento(contact.getDataDeNascimento());
		return this.contactRepository.save(currentContact);
	}
	
	// DELETE Contact
	@DeleteMapping("/{id}")
	public ResponseEntity<Contact> deleteContact(@PathVariable ("id") long contactId){
		Contact currentContact = this.contactRepository.findById(contactId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o ID :" + contactId));
		this.contactRepository.delete(currentContact);
		return ResponseEntity.ok().build();
	}
}
