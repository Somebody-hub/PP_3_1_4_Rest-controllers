package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String FirstName;

    private String LastName;

    private int Age;

    private String email;

    private boolean deleted;

    private String Password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Collection<Role> Roles;

    public User() {
        this.deleted = false;
    }

    public User(String FirstName, String LastName, int Age, String Email, String Password, Collection<Role> roles) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Age = Age;
        this.email = Email;
        this.deleted = false;
        this.Password = Password;
        this.Roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getAge() {
        return Age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Collection<Role> getRoles() {
        return this.Roles;
    }

    public void setRoles(Collection<Role> roles) {
        Roles = roles;
    }

    @Override
    public String toString() {
        return "id = " + this.id
                + "\n" + "FirstName = " + this.FirstName
                + "\n" + "LastName = " + this.LastName
                + "\n" + "Age = " + this.Age
                + "\n" + "Email " + this.email
                + "\n" + "deleted" + this.deleted;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Roles;
    }

    @Override
    public String getPassword() {
        return this.Password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
