package com.ngshop.entity.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String permission;
    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

}
