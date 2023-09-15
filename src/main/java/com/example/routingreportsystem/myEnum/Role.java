package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(0), USER(1);
    private final int code;
    Role(int code) {
        this.code = code;
    }
    public static Role getByCode(int code){
        return switch (code){
            case 0 -> Role.ADMIN;
            case 1 -> Role.USER;
            default -> throw new IllegalArgumentException("Role With Code: ("+ code +") Not Available");
        };
    }
}
