package io.lana.simplespring.product;

import io.lana.simplespring.lib.controller.Identified;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product implements Identified {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String producer;
    @NotNull

    @Column(name = "year_making")
    @Min(0)
    private Integer yearMaking;

    @NotNull
    @Min(0)
    private Double price;
}
