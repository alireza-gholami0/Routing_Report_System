package com.example.routingreportsystem.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReportDto(@NotNull @NotBlank Long id, String type, String description, String point, LocalDateTime reportedAt) {
}
