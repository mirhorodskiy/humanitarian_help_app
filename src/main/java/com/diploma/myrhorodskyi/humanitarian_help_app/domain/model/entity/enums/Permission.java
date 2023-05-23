package com.diploma.myrhorodskyi.humanitarian_help_app.domain.model.entity.enums;

public enum Permission {
    USER("user"),
    ADMIN("admin"),
    VOLUNTEER("volunteer_organization");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
