package com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity;


import javax.persistence.*;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Role;
import lombok.*;

@Entity
@Table(name = "users_credits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "credentials")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "credentials")
    private Volunteer volunteer;



}
