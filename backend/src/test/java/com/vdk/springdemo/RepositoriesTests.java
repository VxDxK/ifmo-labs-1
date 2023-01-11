package com.vdk.springdemo;

import com.vdk.springdemo.entity.Entry;
import com.vdk.springdemo.entity.User;
import com.vdk.springdemo.model.EntryDTO;
import com.vdk.springdemo.model.ShotDTO;
import com.vdk.springdemo.repository.EntryRepository;
import com.vdk.springdemo.repository.UserRepository;
import com.vdk.springdemo.service.ShotService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoriesTests {

    @Autowired
    UserRepository userService;
    @Autowired
    EntryRepository entryRepository;
    @Autowired
    ShotService service;


    @BeforeAll
    void addUsers(){
        User user = new User("user1", "user1password");
        userService.save(user);
        ShotDTO shotDTO = service.checkShot(new EntryDTO(0, 2, 1));
        entryRepository.save(new Entry(shotDTO, user));
        entryRepository.save(new Entry(shotDTO, user));
        entryRepository.save(new Entry(shotDTO, user));
    }

    @Test
    void checkUser1(){
        assertThat(userService.findByUsername("user1").getPassword()).isEqualTo("user1password");
    }

    @Test
    void checkNoOneUser(){
        assertThat(userService.findByUsername("this_user_doesnt_exists")).isNull();
    }

    @Test
    void entryTest(){
        User user = userService.findByUsername("user1");
        assertThat(entryRepository.findByUser(user)).hasSize(3);
        entryRepository.deleteByUser(user);
        assertThat(entryRepository.findByUser(user)).hasSize(0);
    }



}
