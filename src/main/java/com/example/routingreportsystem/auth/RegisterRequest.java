package com.example.routingreportsystem.auth;

import com.example.routingreportsystem.myEnum.Role;
import lombok.Builder;

@Builder
public record RegisterRequest(String email, String password, Role role) { }
