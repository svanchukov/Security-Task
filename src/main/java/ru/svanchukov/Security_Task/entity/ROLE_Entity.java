package ru.svanchukov.Security_Task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class ROLE_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(name = "description")
    private String description;
}
