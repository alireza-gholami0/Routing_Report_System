package com.example.routingreportsystem.myEnum;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN(0), ROLE_USER(1);
    private final int code;
    Role(int code) {
        this.code = code;
    }
    public static Role getByCode(int code){
        return switch (code){
            case 0 -> Role.ROLE_ADMIN;
            case 1 -> Role.ROLE_USER;
            default -> throw new IllegalArgumentException("Role With Code: ("+ code +") Not Available");
        };
    }
}
