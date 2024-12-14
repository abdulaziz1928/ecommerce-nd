package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@Data
public class CreateUserRequest {

    @JsonProperty
    private String username;
    @JsonProperty
    @NotBlank
    private String password;

    @JsonProperty
    @NotBlank
    private String confirmPassword;
}
