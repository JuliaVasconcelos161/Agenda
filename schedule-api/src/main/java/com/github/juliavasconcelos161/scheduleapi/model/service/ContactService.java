package com.github.juliavasconcelos161.scheduleapi.model.service;

import com.github.juliavasconcelos161.scheduleapi.model.entity.Contact;
import org.springframework.data.domain.Page;

import javax.servlet.http.Part;

public interface ContactService {

    Contact saveContact(Contact contact);

    void deleteContact(Integer id);

    Page<Contact> listContacts(Integer page, Integer pageSize);

    void favoriteContact(Integer id);

    byte[] addPicture(Integer id, Part archive);
}
