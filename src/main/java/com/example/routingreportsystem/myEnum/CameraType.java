package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum CameraType {
    SPEED(0), LIGHT(1);
    private final int code;

    CameraType(int code) {
        this.code = code;
    }
    public static CameraType getByCode(int code){
        return switch (code){
            case 0 -> CameraType.SPEED;
            case 1 -> CameraType.LIGHT;
            default -> throw new IllegalArgumentException("CameraType With Code: ("+ code +") Not Available ");
        };
    }
    public static CameraType getByName(String name){
        return switch (name){
            case "SPEED" -> CameraType.SPEED;
            case "LIGHT" -> CameraType.LIGHT;
            default -> throw new IllegalArgumentException("CameraType With name: ("+ name +") Not Available ");
        };
    }
}
