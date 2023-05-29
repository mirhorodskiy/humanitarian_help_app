package com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity;


import javax.persistence.*;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Role;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Status;
import lombok.*;

import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;


//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @OneToOne
    @JoinColumn(name = "credentials_id")
    private UserCredentials credentials;

    @OneToMany(mappedBy = "user")
    private Collection<HelpRequest> helpRequest;

}
