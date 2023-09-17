package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum PoliceType {
    HIDDEN(0), NOT_HIDDEN(1);
    private final int code;

    PoliceType(int code) {
        this.code = code;
    }
    public static PoliceType getByCode(int code){
        return switch (code){
            case 0 -> PoliceType.HIDDEN;
            case 1 -> PoliceType.NOT_HIDDEN;
            default -> throw new IllegalArgumentException("PoliceType With Code: ("+ code +") Not Available ");
        };
    }
    public static PoliceType getByName(String name){
        return switch (name){
            case "HIDDEN" -> PoliceType.HIDDEN;
            case "NOT_HIDDEN" -> PoliceType.NOT_HIDDEN;
            default -> throw new IllegalArgumentException("PoliceType With name: ("+ name +") Not Available ");
        };
    }
}
