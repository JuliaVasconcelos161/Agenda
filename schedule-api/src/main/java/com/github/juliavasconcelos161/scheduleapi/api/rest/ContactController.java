package com.github.juliavasconcelos161.scheduleapi.api.rest;

import com.github.juliavasconcelos161.scheduleapi.model.entity.Contact;
import com.github.juliavasconcelos161.scheduleapi.model.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contacts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContactController {

    private final ContactRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact saveContact (@RequestBody Contact contact)
    {
        return repository.save(contact);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable Integer id)
    {
        repository.deleteById(id);
    }

    @GetMapping
    public List<Contact> contactsList()
    {
        return repository.findAll();
    }

    @PatchMapping("{id}/favorite")
    public void favoriteContact(@PathVariable Integer id, @RequestBody Boolean favorite)
    {
        Optional<Contact> contact = repository.findById(id);
        contact.ifPresent( c -> {
            c.setFavorite((favorite));
            repository.save(c);
        });
    }
}
