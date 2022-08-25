package ru.kata.spring.boot_security.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name="roles")
@Entity
@Data //Сгенерированы геттеры, сеттеры и т. д.
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String role;


    public Role() {
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
