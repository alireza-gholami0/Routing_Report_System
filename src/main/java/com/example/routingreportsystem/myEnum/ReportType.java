package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum ReportType {
    ACCIDENT(0), TRAFFIC(1), CAMERA(2);
    private final int code;

    ReportType(int code) {
        this.code = code;
    }
    public static ReportType getByCode(int code){
        return switch (code){
            case 0 -> ReportType.ACCIDENT;
            case 1 -> ReportType.TRAFFIC;
            case 2 -> ReportType.CAMERA;
            default -> throw new IllegalArgumentException("ReportType With Code: ("+ code +") Not Available");
        };
    }
}
