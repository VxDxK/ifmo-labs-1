package com.vdk.springdemo.controller;

import com.vdk.springdemo.entity.Entry;
import com.vdk.springdemo.entity.User;
import com.vdk.springdemo.model.EntryDTO;
import com.vdk.springdemo.model.ShotDTO;
import com.vdk.springdemo.repository.EntryRepository;
import com.vdk.springdemo.repository.UserNoEncodeRepository;
import com.vdk.springdemo.security.UserService;
import com.vdk.springdemo.service.ShotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;
    private final EntryRepository entryRepository;
    private final ShotService shotService;

    @GetMapping(value = "/shots", produces = "application/json")
    ResponseEntity<?> getUserShotsPost(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.findByUser(user).stream().map(ShotDTO::new).collect(Collectors.toList()));
    }

    @PostMapping("/shot")
    ResponseEntity<?> shot(@Valid @RequestBody EntryDTO shot, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        ShotDTO shotDTO = shotService.checkShot(shot);
        entryRepository.save(new Entry(shotDTO, user));
        return ResponseEntity.ok("Ok: " + shotDTO);
    }
}
