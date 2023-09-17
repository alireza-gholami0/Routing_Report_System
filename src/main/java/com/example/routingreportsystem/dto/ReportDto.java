package com.example.routingreportsystem.dto;


import java.time.LocalDateTime;

public record ReportDto(String type, String description, String point, LocalDateTime reportedAt) {
}
