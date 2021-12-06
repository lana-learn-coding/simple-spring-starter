package io.lana.simplespring.product;

import io.lana.simplespring.lib.controller.Identified;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product implements Identified {
    @Id
    @NotBlank
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

    private String images;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
