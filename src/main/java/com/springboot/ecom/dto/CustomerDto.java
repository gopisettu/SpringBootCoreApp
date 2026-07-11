package com.springboot.ecom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;


public record CustomerDto(
        @NotBlank(message = "Name is mandatory")
        @Pattern(regexp="[a-z]+")
        String name,
        @NotBlank
        String city
) {
}
