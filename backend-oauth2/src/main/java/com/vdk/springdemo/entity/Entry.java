package com.vdk.springdemo.entity;

import com.vdk.springdemo.model.ShotDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vdk_lab4_entry")
public class Entry {
    @Id
    @SequenceGenerator(name = "vdk_lab4_entry_id_seq", sequenceName = "vdk_lab4_entry_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vdk_lab4_entry_id_seq")
    private long id;
    private double x;
    private double y;
    private double r;
    private boolean result;
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    public Entry(ShotDTO shotDTO, User user) {
        this.x = shotDTO.getX();
        this.y = shotDTO.getY();
        this.r = shotDTO.getR();
        this.result = shotDTO.isResult();
        this.time = shotDTO.getTime();
        this.user = user;
    }
}

