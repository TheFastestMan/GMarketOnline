package ru.rail.gmarketonline.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Product name should not be empty")
    private String productName;
    @NotEmpty(message = "Description should not be empty")
    private String description;
    @NotNull(message = "Quantity should not be null")
    @Min(value = 1, message = "Quantity should not be less than 1")
    private Integer quantity;
    @NotNull(message = "Price should not be null")
    private Double price;
}
