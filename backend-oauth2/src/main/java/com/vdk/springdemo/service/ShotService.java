package com.vdk.springdemo.service;

import com.vdk.springdemo.model.EntryDTO;
import com.vdk.springdemo.model.ShotDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@NoArgsConstructor
public class ShotService {

    public ShotDTO checkShot(EntryDTO entry){
        ShotDTO ans = new ShotDTO(entry);
        ans.setTime(LocalDateTime.now());
        ans.setResult(entry.getR() != 0 && isHit(entry.getX() / entry.getR(), entry.getY() / entry.getR()));
        return ans;
    }

    private boolean isHit(double x, double y){
        if(x < 0 && y > 0)
            return false;

        if(x >= 0 && y >= 0)
            return x <= 1 && y <= 0.5;

        if(x > 0 && y < 0)
            return y >= x - 1;

        if(x < 0 && y < 0)
            return x*x + y*y <= 1;

        return false;
    }

}
