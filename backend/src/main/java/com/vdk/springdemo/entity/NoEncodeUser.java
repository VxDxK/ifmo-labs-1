package com.vdk.springdemo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vdk_lab4_user_noencode")
public class NoEncodeUser{
    @Id
    @SequenceGenerator(name = "vdk_lab4_user_noencode_id_seq", sequenceName = "vdk_lab4_user_noencode_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vdk_lab4_user_noencode_id_seq")
    private long id;
    private String username;
    private String password;

    public NoEncodeUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
