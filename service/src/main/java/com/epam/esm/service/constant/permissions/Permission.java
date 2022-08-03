package com.epam.esm.service.constant.permissions;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Permission implements GrantedAuthority {
    MARKET_READ((byte)1, "market_read"),
    MARKET_WRITE((byte)2, "market_write"),
    PERSONAL_READ((byte)3, "personal_read"),
    MANAGEMENT_READ((byte) 4, "management_read"),
    MANAGEMENT_WRITE((byte) 5, "management_write");

    private final byte id;
    private final String alias;

    public byte getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public static Optional<Permission> getById(byte id){
        Optional<Permission> retVal = Optional.empty();
        for(Permission permission: Permission.values()){
            if (permission.id == id){
                retVal = Optional.of(permission);
            }
        }
        return retVal;
    }

    @Override
    public String getAuthority() {
        return alias;
    }
}
