package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum TrafficType {
    LIGHT(0), SEMI_HEAVY(1) , HEAVY(2);
    private final int code;

    TrafficType(int code) {
        this.code = code;
    }
    public static TrafficType getByCode(int code){
        return switch (code){
            case 0 -> TrafficType.LIGHT;
            case 1 -> TrafficType.SEMI_HEAVY;
            case 2 -> TrafficType.HEAVY;
            default -> throw new IllegalArgumentException("TrafficType With Code: ("+ code +") Not Available ");
        };
    }
    public static TrafficType getByName(String name){
        return switch (name){
            case "LIGHT" -> TrafficType.LIGHT;
            case "SEMI_HEAVY" -> TrafficType.SEMI_HEAVY;
            case "HEAVY" -> TrafficType.HEAVY;
            default -> throw new IllegalArgumentException("TrafficType With name: ("+ name +") Not Available ");
        };
    }
}
