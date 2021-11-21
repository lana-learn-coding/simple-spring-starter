package io.lana.simplespring.product;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Product {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String producer;
    @NotNull
    @Min(0)
    private Integer yearMaking;
    @NotNull
    @Min(0)
    private Double price;
}
