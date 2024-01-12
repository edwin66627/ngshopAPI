package com.ngshop.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngshop.entity.Address;
import com.ngshop.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User implements UserDetails, CredentialsContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 1000)
    private String password;
    @Builder.Default
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Builder.Default // So that Lombok doesn't set the variable to null. It sets the value we specify
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority -> {
                    return new SimpleGrantedAuthority(authority.getPermission());
                })
                .collect(Collectors.toSet());
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();
    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<Role> roles;
    @Override
    public void eraseCredentials() {
        this.password = null;
    }
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
