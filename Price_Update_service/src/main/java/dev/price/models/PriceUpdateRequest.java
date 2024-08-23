package dev.price.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PriceUpdateRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotNull(message = "New price is required")
    @Min(value = 0, message = "Price must be positive")
    private double newPrice;
}
