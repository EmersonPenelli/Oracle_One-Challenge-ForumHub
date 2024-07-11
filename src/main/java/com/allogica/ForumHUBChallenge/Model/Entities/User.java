package com.allogica.ForumHUBChallenge.Model.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.enabled = true;
        this.topics = new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
//        TODO: To evaluate how this methods work and if it is necessary to implement it;
        //        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
//        TODO: To evaluate how this methods work and if it is necessary to implement it;
        //        return UserDetails.super.isAccountNonLocked();

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
//        TODO: To evaluate how this methods work and if it is necessary to implement it;
//        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true;
//        TODO: To evaluate how this methods work and if it is necessary to implement it;
        //        return UserDetails.super.isEnabled();
    }

    @OneToMany(mappedBy = "author")
    private List<Topic> topics;

    @OneToMany(mappedBy = "author")
    private List<Answer> answers;
}
