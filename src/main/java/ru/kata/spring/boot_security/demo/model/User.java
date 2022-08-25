package ru.kata.spring.boot_security.demo.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Table(name="users")
@Entity
@Data //Сгенерированы геттеры, сеттеры и т. д.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 35, message = "Name should contain up to 35 characters")
    private String name;

    @Column(name = "age")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 122, message = "Age should be less than 122")
    private int age;

    @Column(name = "country")
    @NotEmpty(message = "Country should not be empty")
    private String country;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable (name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public User() {

    }
}
