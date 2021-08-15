package com.ld.filearchive.models;

import org.springframework.security.core.GrantedAuthority;

// Enum Role, содержит возможные варианты ролей пользователей.

public enum Role implements GrantedAuthority {
    USER, EDITOR, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
