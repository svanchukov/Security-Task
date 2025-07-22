package ru.svanchukov.Security_Task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.svanchukov.Security_Task.dto.AuthResponseDTO;
import ru.svanchukov.Security_Task.dto.LoginRequestDTO;
import ru.svanchukov.Security_Task.dto.RegisterRequestDTO;
import ru.svanchukov.Security_Task.dto.UserDTO;
import ru.svanchukov.Security_Task.entity.User;
import ru.svanchukov.Security_Task.repository.UserRepository;
import ru.svanchukov.Security_Task.service.UserDetailsServiceImpl;
import ru.svanchukov.Security_Task.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    public final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("{id}")
    public String getById(@PathVariable("id") long id) {
        UserDTO userDTO = userService.getUserById(id);
        return String.valueOf(new ResponseEntity<>(userDTO, HttpStatus.OK));
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        UserDTO userDTO = userService.registerUser(registerRequestDTO);
        return String.valueOf(new ResponseEntity<>(userDTO, HttpStatus.CREATED));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO authResponseDTO = userService.login(loginRequestDTO);
        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
