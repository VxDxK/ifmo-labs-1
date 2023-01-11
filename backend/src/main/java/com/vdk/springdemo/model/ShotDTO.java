package com.vdk.springdemo.model;

import com.vdk.springdemo.entity.Entry;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShotDTO {
    private double x;
    private double y;
    private double r;
    private boolean result;
    private LocalDateTime time;

    public ShotDTO(Entry entry){
        this.x = entry.getX();
        this.y = entry.getY();
        this.r = entry.getR();
        this.result = entry.isResult();
        this.time = entry.getTime();
    }

    public ShotDTO(EntryDTO entry){
        this.x = entry.getX();
        this.y = entry.getY();
        this.r = entry.getR();
    }

}
