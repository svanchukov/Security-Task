package ru.svanchukov.Security_Task.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String login;

    private String password;
}
