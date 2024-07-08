package com.o2dent.lib.accounts.defaults;

public enum Roles {
    ADMIN ("ADMIN"),
    PERSONNEL ("PERSONNEL"),
    CUSTOMER ("CUSTOMER");
    private final String roleName;
    Roles(String roleName) {
        this.roleName = roleName;
    }
}
