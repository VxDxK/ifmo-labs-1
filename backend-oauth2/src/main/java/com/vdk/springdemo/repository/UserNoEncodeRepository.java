package com.vdk.springdemo.repository;

import com.vdk.springdemo.entity.NoEncodeUser;
import com.vdk.springdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNoEncodeRepository extends JpaRepository<NoEncodeUser, Long> {

}
