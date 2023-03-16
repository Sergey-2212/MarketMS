package ru.gb.authservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.api.JwtResponse;
import ru.gb.api.RegistrationRequest;
import ru.gb.authservice.entities.User;
import ru.gb.authservice.exceptions.PasswordsDontMatchException;
import ru.gb.authservice.exceptions.UserIsAlreadyExistException;
import ru.gb.authservice.services.UserService;
import ru.gb.authservice.utils.JwtTokenUtil;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/newuser")
    public User createUser () {
        return userService.createNewUser("test", "test");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest request) {
        if(userService.isUserAlreadyExist(request.getUsername())){
            throw new UserIsAlreadyExistException("Пользователь с таким логином уже существует!");
        }

        if(!request.getPassword().equals(request.getConfirmation())) {
            throw new PasswordsDontMatchException("Пароли не совпадают!");
        }
        log.info("Controller Before createNewUser");
        userService.createNewUser(request.getUsername(), request.getPassword());

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
