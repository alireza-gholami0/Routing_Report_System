package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum ReportStatus {
    UNKNOWN(0), ACCEPTED(1), NOT_ACCEPTED(2), TERMINATED(3);
    private final int code;

    ReportStatus(int code) {
        this.code = code;
    }
    public static ReportStatus getByCode(int code){
        return switch (code){
            case 0 -> ReportStatus.UNKNOWN;
            case 1 -> ReportStatus.ACCEPTED;
            case 2 -> ReportStatus.NOT_ACCEPTED;
            case 3 -> ReportStatus.TERMINATED;
            default -> throw new IllegalArgumentException("ReportStatus With Code: ("+ code +") Not Available");
        };
    }
}
