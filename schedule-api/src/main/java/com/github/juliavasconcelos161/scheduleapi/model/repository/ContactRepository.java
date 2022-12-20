package com.github.juliavasconcelos161.scheduleapi.model.repository;

import com.github.juliavasconcelos161.scheduleapi.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
