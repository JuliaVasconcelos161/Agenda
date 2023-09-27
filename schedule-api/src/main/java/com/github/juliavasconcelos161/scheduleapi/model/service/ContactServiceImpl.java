package com.github.juliavasconcelos161.scheduleapi.model.service;

import com.github.juliavasconcelos161.scheduleapi.model.entity.Contact;
import com.github.juliavasconcelos161.scheduleapi.model.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }
    @Override
    public void deleteContact(Integer id){
        contactRepository.deleteById(id);
    }
    @Override
    public Page<Contact> listContacts(Integer page, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        return contactRepository.findAll(pageRequest);
    }
    @Override
    public void favoriteContact(Integer id){
        Optional<Contact> contact = contactRepository.findById(id);
        contact.ifPresent( c -> {
            boolean favorite = c.getFavorite() == Boolean.TRUE;
            c.setFavorite(!favorite);
            contactRepository.save(c);
        });
    }
    @Override
    public byte[] addPicture(Integer id, Part archive) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map( c -> {
            try{
                InputStream is = archive.getInputStream();
                byte[] bytes = new byte[(int)archive.getSize()];
                IOUtils.readFully(is, bytes);
                c.setPicture(bytes);
                contactRepository.save(c);
                is.close();
                return bytes;
            } catch(IOException e) {
                return null;
            }
        }).orElseThrow(null);
    }



}
