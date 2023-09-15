package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum AccidentType {
    LIGHT(0), HEAVY(1);
    private final int code;

    AccidentType(int code) {
        this.code = code;
    }
    public static AccidentType getByCode(int code){
        return switch (code){
            case 0 -> AccidentType.LIGHT;
            case 1 -> AccidentType.HEAVY;
            default -> throw new IllegalArgumentException("AccidentType With Code: ("+ code +") Not Available ");
        };
    }
}
