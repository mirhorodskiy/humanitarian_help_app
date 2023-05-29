package com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity;

import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Role;
import com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "volunteer")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name="website")
    private String website;

    @Column(name="checked")
    private boolean isChecked;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    //@OneToOne(cascade = CascadeType.ALL, mappedBy = "volunteer")
    @OneToOne
    @JoinColumn(name = "credentials_id")
    private UserCredentials credentials;


    @OneToMany(mappedBy = "volunteer")
    private Collection<HelpRequest> helpRequest;


}
