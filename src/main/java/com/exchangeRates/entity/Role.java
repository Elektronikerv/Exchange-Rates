package com.exchangeRates.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public enum Role implements GrantedAuthority {

    USER(1L, "USER"),
    ADMIN(2L, "ADMIN");

    private final Long id;
    private final String name;

    Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static Role findById(Long id) {
        for (Role role: Role.values()) {
            if (Objects.equals(id, role.getId()))
                return role;
        }
        return  null;
    }
}
