package ru.svanchukov.Security_Task.dto;

import lombok.Data;
import ru.svanchukov.Security_Task.entity.ROLE;

import java.util.Set;

@Data
public class RegisterRequestDTO {

    private String login;

    private String email;

    private String password;

    private Set<ROLE> roles;
}
