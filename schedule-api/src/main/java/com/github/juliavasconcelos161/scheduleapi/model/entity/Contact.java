package com.github.juliavasconcelos161.scheduleapi.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String email;

    @Column
    private Boolean favorite;

    @Column
    @Lob
    private byte[] picture;
}
