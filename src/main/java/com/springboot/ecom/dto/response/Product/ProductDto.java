package com.springboot.ecom.dto.response.Product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import org.aspectj.bridge.IMessage;

public record ProductDto(
        @NotBlank(message = "Field cannot be null")
        String title,
        @NotBlank(message = "Field cannot be null")
        String description,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        @Min(value = 500, message = "Minimum price is 500")
        Double price
) {
}
