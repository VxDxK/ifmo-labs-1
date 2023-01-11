package com.vdk.springdemo.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class EntryDTO {
    @Min(-2)
    @Max(2)
    @NonNull
    private double x;

    @Min(-5)
    @Max(5)
    @NonNull
    private double y;

    @Min(-2)
    @Max(2)
    @NonNull
    private double r;
}
