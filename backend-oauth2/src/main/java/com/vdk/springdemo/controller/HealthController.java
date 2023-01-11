package com.vdk.springdemo.controller;

import com.vdk.springdemo.model.UserDTO;
import com.vdk.springdemo.repository.UserNoEncodeRepository;
import com.vdk.springdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/health")
public class HealthController {
    private final UserRepository userRepository;
    private final UserNoEncodeRepository userNoEncodeRepository;

    @GetMapping("/all")
    ResponseEntity<?> all(){
        return ResponseEntity.ok(userRepository.findAll().stream().map(x -> new UserDTO(x.getUsername(), x.getPassword())).collect(Collectors.toList()));
    }

    @GetMapping("/noencode")
    ResponseEntity<?> allNoEncode(){
        return ResponseEntity.ok(userNoEncodeRepository.findAll().stream().map(x -> new UserDTO(x.getUsername(), x.getPassword())).collect(Collectors.toList()));
    }
}
