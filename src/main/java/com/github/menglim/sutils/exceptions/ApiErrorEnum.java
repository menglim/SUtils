package com.github.menglim.sutils.exceptions;

public enum ApiErrorEnum {
    USER_NOT_ACTIVE,
    DEVICE_NOT_ACTIVE,
    USER_NOT_FOUND,
    SESSION_EXPIRED,
    USER_FULL_NAME_REQUIRED,
    OTP_EXPIRED,
    OTP_INVALID,
    CONTENT_NOT_FOUND,
    NULL_RESPONSE,
    CANNOT_GENERATE_DEVICE_KEY,
    INVALID_DEVICE_TOKEN_FORMAT,
    DEVICE_KEY_NOT_FOUND,
    VALIDATION_ERROR,
    RESEND_OTP_CODE_NOT_MATCH,
    JWT_TOKEN_EXPIRED
}
