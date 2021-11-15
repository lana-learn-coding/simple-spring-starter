package io.lana.simplespring.app;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Message {
    @NotBlank
    private String greet;

    @NotBlank
    private String target;
}
