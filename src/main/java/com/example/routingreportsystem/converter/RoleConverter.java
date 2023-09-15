package com.example.routingreportsystem.converter;

import com.example.routingreportsystem.myEnum.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role.getCode();
    }

    @Override
    public Role convertToEntityAttribute(Integer integer) {
        return Role.getByCode(integer);
    }
}
