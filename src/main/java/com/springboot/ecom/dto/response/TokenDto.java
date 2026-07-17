package com.springboot.ecom.dto.response;

public record TokenDto(
        String token,
        String expiration,
        String role
) {
}
