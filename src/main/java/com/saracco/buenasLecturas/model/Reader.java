package com.saracco.buenasLecturas.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.*;


@Entity
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email valido")
    private String email;

    @Size(min = 4, message = "Debe tener al menos 4 caracteres")
    private String password;

    @OneToMany(mappedBy = "reader")
    private List<ReadingList> readingList;

    public Reader() {
    }

    public Reader(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Reader(String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password = password;

        this.readingList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
