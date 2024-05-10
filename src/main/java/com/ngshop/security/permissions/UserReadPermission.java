package com.ngshop.security.permissions;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('ADMIN')")
public @interface UserReadPermission {
    // The @ symbol denotes an annotation type definition.
    // That means it is not really an interface, but rather a new annotation type -- to be used as a function modifier,
    // such as @override.
}