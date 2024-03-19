package com.ngshop.constant;

public class SecurityConstant {
    public static final long TOKEN_EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 999_000_000; // 11 days 13 hrs 30 mins expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String COMPANY_NAME = "Company Name";
    public static final String COMPANY_ADMINISTRATION = "Student Management Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";

}
