package com.test.shuffle.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShuffleRequest {

    @NotNull(message = "Number cannot be null")
    @Min(value = 1, message = "Number must be at least 1")
    @Max(value = 1000, message = "Number must be at most 1000")
    private int number;
}
