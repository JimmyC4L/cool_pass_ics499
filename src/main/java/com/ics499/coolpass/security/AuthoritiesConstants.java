package com.ics499.coolpass.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String BUSINESSOWNER  = "ROLE_BUSINESS_OWNER";

    private AuthoritiesConstants() {
    }
}
