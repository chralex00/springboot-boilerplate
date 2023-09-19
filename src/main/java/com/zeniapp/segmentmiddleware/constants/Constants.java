package com.zeniapp.segmentmiddleware.constants;

public class Constants {
    // Numeric Limits
    public static final int MIN_OFFSET_VALUE = 0;
    public static final int MAX_OFFSET_VALUE = Integer.MAX_VALUE;
    public static final int MIN_LIMIT_VALUE = 0;
    public static final int MAX_LIMIT_VALUE = 50;
    public static final int STRING_FILTER_MIN_LENGTH = 1;
    public static final int STRING_FILTER_MAX_LENGTH = 256;
    public static final long MIN_TIMESTAMP_VALUE = 0;
    public static final long MAX_TIMESTAMP_VALUE = Long.MAX_VALUE;

    // Length Limits
    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 256;
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 64;
    public static final int FIRST_NAME_MIN_LENGTH = 2;
    public static final int FIRST_NAME_MAX_LENGTH = 128;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 128;
    public static final int PASSWORD_MIN_LENGTH = 10;
    public static final int PASSWORD_MAX_LENGTH = 64;
    public static final int IDENTIFIER_MIN_LENGTH = 2;
    public static final int IDENTIFIER_MAX_LENGTH = 256;
    public static final int ROLE_MIN_LENGTH = 2;
    public static final int ROLE_MAX_LENGTH = 128;

    // Regular Expressions
    public static final String ISO8601_FORMAT_REGEXP = "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
    public static final String SQL_ORDER_BY_REGEXP = "^(asc|desc)$";
    public static final String USERNAME_REGEXP = "^[A-Za-z0-9]*$";
    public static final String FIRST_NAME_REGEXP = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    public static final String LAST_NAME_REGEXP = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    public static final String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$";
    public static final String ACCOUNT_SORT_FIELDS_REGEXP = "^(email|username|firstName|lastName|createdOn|updatedOn)$";
    public static final String ROLE_REGEXP = "^(USER|ADMIN)$";
}
