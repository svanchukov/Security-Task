package ru.svanchukov.Security_Task.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.svanchukov.Security_Task.dto.AuthResponseDTO;
import ru.svanchukov.Security_Task.dto.LoginRequestDTO;
import ru.svanchukov.Security_Task.dto.RegisterRequestDTO;
import ru.svanchukov.Security_Task.dto.UserDTO;
import ru.svanchukov.Security_Task.entity.ROLE;
import ru.svanchukov.Security_Task.entity.User;
import ru.svanchukov.Security_Task.jwt.JwtService;
import ru.svanchukov.Security_Task.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDTO registerUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.existsByLogin(registerRequestDTO.getLogin())) {
            throw new RuntimeException("Пользователь с данным login уже существует");
        }

        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Пользователь с данным email уже существует");
        }

        String encodePassword = bCryptPasswordEncoder.encode(registerRequestDTO.getPassword());

        User user = new User();
        user.setLogin(registerRequestDTO.getLogin());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(encodePassword);
        user.setRoles(registerRequestDTO.getRoles());

        UserDTO tempDTO = new UserDTO();
        tempDTO.setLogin(user.getLogin());
        tempDTO.setRoles(user.getRoles());

        String accessToken = jwtService.generateToken(tempDTO);
        user.setToken(accessToken);

        // Сохранение пользователя в базе данных
        User savedUser = userRepository.save(user);

        // Преобразование в DTO перед возвратом
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setLogin(savedUser.getLogin());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setRoles(savedUser.getRoles());

        return userDTO;
    }

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByLogin(loginRequestDTO.getLogin())
                .orElseThrow(() -> new RuntimeException("Invalid login"));

        if (!bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setRoles(user.getRoles());

        String accessToken = jwtService.generateToken(userDTO);
        String refreshToken = jwtService.refreshToken(userDTO);
        System.out.println("Access token: " + accessToken);
        System.out.println("Refresh token: " + refreshToken);

        return new AuthResponseDTO(accessToken, refreshToken);

    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToDTO(user);

    }

    private UserDTO convertToDTO(User user) {
        // Преобразование User в UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }

}
