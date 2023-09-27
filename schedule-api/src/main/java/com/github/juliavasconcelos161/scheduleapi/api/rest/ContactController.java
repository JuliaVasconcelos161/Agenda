package com.github.juliavasconcelos161.scheduleapi.api.rest;

import com.github.juliavasconcelos161.scheduleapi.model.entity.Contact;
import com.github.juliavasconcelos161.scheduleapi.model.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;

@RestController
@RequestMapping("api/contacts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new contact.")
    public Contact saveContact (@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete contact by id")
    public void deleteContact(@PathVariable Integer id) {
        contactService.deleteContact(id);
    }

    @GetMapping
    @Operation(summary = "Get all contacts")
    public Page<Contact> contactsList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return contactService.listContacts(page, pageSize);
    }

    @PatchMapping("{id}/favorite")
    @Operation(summary = "Favorite contacts")
    public void favoriteContact(@PathVariable Integer id) {
        contactService.favoriteContact(id);
    }

    @PutMapping("{id}/picture")
    @Operation(summary = "Add picture after saving contact")
    public byte[] addPicture(@PathVariable Integer id, @RequestParam ("picture") Part archive) {
        return contactService.addPicture(id, archive);
    }
}
