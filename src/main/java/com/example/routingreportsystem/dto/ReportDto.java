package com.example.routingreportsystem.dto;


import java.time.LocalDateTime;

public record ReportDto(Long id, String type, String description, String point, LocalDateTime reportedAt) {
}
