package com.example.routingreportsystem.auth;

import com.example.routingreportsystem.myEnum.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegisterRequest(@NotNull @NotBlank String email, @NotNull @NotBlank String password,
                              @NotNull @NotBlank Role role) { }
