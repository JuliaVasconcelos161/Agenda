package com.github.juliavasconcelos161.scheduleapi.api.rest;

import com.github.juliavasconcelos161.scheduleapi.model.entity.Contact;
import com.github.juliavasconcelos161.scheduleapi.model.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
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
    public Page<Contact> contactsList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    )
    {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        return repository.findAll(pageRequest);
    }

    @PatchMapping("{id}/favorite")
    public void favoriteContact(@PathVariable Integer id)
    {
        Optional<Contact> contact = repository.findById(id);
        contact.ifPresent( c -> {
            boolean favorite = c.getFavorite() == Boolean.TRUE;
            c.setFavorite(!favorite);
            repository.save(c);
        });
    }

    @PutMapping("{id}/picture")
    public byte[] addPicture(@PathVariable Integer id, @RequestParam ("picture") Part archive)
    {
        Optional<Contact> contact = repository.findById(id);
        return contact.map( c -> {
            try{
                InputStream is = archive.getInputStream();
                byte[] bytes = new byte[(int)archive.getSize()];
                IOUtils.readFully(is, bytes);
                c.setPicture(bytes);
                repository.save(c);
                is.close();
                return bytes;
            } catch(IOException e) {
                return null;
            }
        }).orElseThrow(null);
    }
}
