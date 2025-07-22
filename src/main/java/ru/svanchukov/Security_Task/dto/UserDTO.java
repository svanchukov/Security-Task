package ru.svanchukov.Security_Task.dto;

import jakarta.persistence.*;
import lombok.Data;
import ru.svanchukov.Security_Task.entity.ROLE;

import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    private String login;

    private String password;

    private String email;

    private Set<ROLE> roles;
}
