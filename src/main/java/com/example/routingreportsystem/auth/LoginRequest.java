package com.example.routingreportsystem.auth;

import lombok.Builder;
@Builder
public record LoginRequest(String email, String password) {}
