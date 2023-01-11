package com.vdk.springdemo.controller;

import com.vdk.springdemo.controller.exception.InvaliUserOrPasswordException;
import com.vdk.springdemo.controller.exception.UserAlreadyExistsException;
import com.vdk.springdemo.entity.NoEncodeUser;
import com.vdk.springdemo.entity.User;
import com.vdk.springdemo.model.JwtDTO;
import com.vdk.springdemo.model.UserDTO;
import com.vdk.springdemo.repository.UserNoEncodeRepository;
import com.vdk.springdemo.security.UserService;
import com.vdk.springdemo.security.JWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserService userService;
    private final JWT jwt;
    private final UserNoEncodeRepository userNoEncodeRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO userDTO){
        User us = userService.loadUserByUsername(userDTO.getUsername());
        if(us == null || !encoder.matches(userDTO.getPassword(), us.getPassword())){
            throw new InvaliUserOrPasswordException();
        }
        String jwtS = jwt.generateToken(userDTO.getUsername());
        return ResponseEntity.ok(new JwtDTO(userDTO.getUsername(), jwtS));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO){;
        if(userService.loadUserByUsername(userDTO.getUsername()) != null){
            throw new UserAlreadyExistsException();
        }
        userService.addUser(new User(userDTO.getUsername(), encoder.encode(userDTO.getPassword())));
        userNoEncodeRepository.save(new NoEncodeUser(userDTO.getUsername(), userDTO.getPassword()));
        return ResponseEntity.ok("Registered");
    }

}
