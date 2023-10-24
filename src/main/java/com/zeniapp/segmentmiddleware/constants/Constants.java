package com.zeniapp.segmentmiddleware.constants;

public class Constants {
    // Numeric Limits
    public static final int MIN_OFFSET_VALUE = 0;
    public static final int MAX_OFFSET_VALUE = Integer.MAX_VALUE;
    public static final int MIN_LIMIT_VALUE = 1;
    public static final int MAX_LIMIT_VALUE = 50;
    public static final int STRING_FILTER_MIN_LENGTH = 1;
    public static final int STRING_FILTER_MAX_LENGTH = 256;
    public static final long MIN_TIMESTAMP_VALUE = 0;
    public static final long MAX_TIMESTAMP_VALUE = Long.MAX_VALUE;
    public static final int MIN_API_COUNTER_VALUE = 0;
    public static final int MAX_API_COUNTER_VALUE = Integer.MAX_VALUE;
    public static final int MIN_METS_VALUE = 0;
    public static final int MAX_METS_VALUE = 50;
    public static final int MIN_MACRONUTRIENT_VALUE = 0; // expressed in grams
    public static final int MAX_MACRONUTRIENT_VALUE = 1000; // expressed in grams
    public static final int MIN_REFERENCE_PORTION_QUANTITY_VALUE = 0; // expressed in grams
    public static final int MAX_REFERENCE_PORTION_QUANTITY_VALUE = 1000; // expressed in grams
    public static final int MIN_ENERGY_VALUE = 0; // expressed in kcal
    public static final int MAX_ENERGY_VALUE = 9000; // expressed in kcal
    public static final int MIN_CHOLESTEROL_VALUE = 0; // expressed in milligrams
    public static final int MAX_CHOLESTEROL_VALUE = 10_000; // expressed in milligrams

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
    public static final int FOOD_NAME_MIN_LENGTH = 1;
    public static final int FOOD_NAME_MAX_LENGTH = 256;
    public static final int ACTIVITY_NAME_MIN_LENGTH = 1;
    public static final int ACTIVITY_NAME_MAX_LENGTH = 256;
    public static final int MUSCLE_NAME_MIN_LENGTH = 1;
    public static final int MUSCLE_NAME_MAX_LENGTH = 256;
    public static final int EXERCISE_NAME_MIN_LENGTH = 1;
    public static final int EXERCISE_NAME_MAX_LENGTH = 256;
    public static final int FOOD_CATEGORY_MIN_LENGTH = 1;
    public static final int FOOD_CATEGORY_MAX_LENGTH = 64;
    public static final int MUSCLE_ORIGIN_MIN_LENGTH = 1;
    public static final int MUSCLE_ORIGIN_MAX_LENGTH = 256;
    public static final int MUSCLE_INSERTION_MIN_LENGTH = 1;
    public static final int MUSCLE_INSERTION_MAX_LENGTH = 256;
    public static final int MUSCLE_FUNCTIONS_MIN_LENGTH = 1;
    public static final int MUSCLE_FUNCTIONS_MAX_LENGTH = 256;
    public static final int MUSCLE_FORM_MIN_LENGTH = 1;
    public static final int MUSCLE_FORM_MAX_LENGTH = 32;
    public static final int MUSCLE_DIMENSION_MIN_LENGTH = 1;
    public static final int MUSCLE_DIMENSION_MAX_LENGTH = 32;
    public static final int ACTIVITY_DESCRIPTION_MIN_LENGTH = 1;
    public static final int ACTIVITY_DESCRIPTION_MAX_LENGTH = 512;
    public static final int ACTIVITY_TYPE_MIN_LENGTH = 1;
    public static final int ACTIVITY_TYPE_MAX_LENGTH = 64;
    public static final int EXERCISE_EXECUTION_MIN_LENGTH = 1;
    public static final int EXERCISE_EXECUTION_MAX_LENGTH = 512;
    public static final int EXERCISE_COMMON_ERRORS_MIN_LENGTH = 1;
    public static final int EXERCISE_COMMON_ERRORS_MAX_LENGTH = 512;
    public static final int EXERCISE_DIFFICULTY_MIN_LENGTH = 1;
    public static final int EXERCISE_DIFFICULTY_MAX_LENGTH = 64;
    public static final int EXERCISE_CATEGORY_MIN_LENGTH = 1;
    public static final int EXERCISE_CATEGORY_MAX_LENGTH = 64;
    public static final int EXERCISE_TYPE_MIN_LENGTH = 1;
    public static final int EXERCISE_TYPE_MAX_LENGTH = 64;
    public static final int MAIN_EXERCISES_MAX_LENGTH = 15;
    public static final int TAGS_MAX_LENGTH = 15;
    public static final int AGONIST_MUSCLES_MAX_LENGTH = 5;
    public static final int ANTAGONIST_MUSCLES_MAX_LENGTH = 5;
    public static final int SYNERGISTIC_MUSCLES_MAX_LENGTH = 5;
    public static final int FIXATOR_MUSCLES_MAX_LENGTH = 5;
    public static final int VITAMINS_MAX_LENGTH = 5;
    public static final int MINERALS_MAX_LENGTH = 5;

    // Regular Expressions
    public static final String ISO8601_FORMAT_REGEXP = "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
    public static final String SQL_ORDER_BY_REGEXP = "^(asc|desc)$";
    public static final String USERNAME_REGEXP = "^[A-Za-z0-9]*$";
    public static final String FIRST_NAME_REGEXP = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    public static final String LAST_NAME_REGEXP = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
    public static final String PASSWORD_REGEXP = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$";
    public static final String ROLE_REGEXP = "^(USER|ADMIN)$";
    public static final String ACTIVITY_SORT_FIELDS_REGEXP = "^(name|type|mets)$";
    public static final String ACCOUNT_SORT_FIELDS_REGEXP = "^(email|username|firstName|lastName|createdOn|updatedOn)$";
    public static final String SESSION_SORT_FIELDS_REGEXP = "^(apiCounter|createdOn|lastActivityOn)$";
    public static final String MUSCLE_SORT_FIELDS_REGEXP = "^(name|form|dimension|origin|insertion|functions)$";
    public static final String FOOD_SORT_FIELDS_REGEXP = "^(name|category|energy|proteins|simplexCarbs|complexCarbs|saturatedFats|unsaturatedFats|fibers|cholesterol)$";
    public static final String EXERCISE_SORT_FIELDS_REGEXP = "^(name|difficulty|category|type)$";
    public static final String EXERCISE_DIFFICULTY_REGEXP = "^(EASY|MEDIUM|DIFFICULT|EXTREME)$";
    public static final String EXERCISE_CATEGORY_REGEXP = "^(FUNDAMENTAL|COMPLEMENTARY|INSULATING)$";
    public static final String EXERCISE_TYPE_REGEXP = "^(MULTIARTICULAR|BIARTICULAR|SINGLEARTICULAR)$";
    public static final String MUSCLE_FORM_REGEXP = "^(FUSIFORM|PARALLEL|CONVERGENT|UNIPENNATE|BIPINNATE|MULTIPINNATE|CIRCULAR)$";
    public static final String MUSCLE_DIMENSION_REGEXP = "^(SMALL|MEDIUM|BIG)$";
    public static final String ACTIVITY_TYPE_REGEXP = "^(QUOTIDIAN|SPORT|DISCIPLINE|WORK|OTHER)$";
    public static final String VITAMIN_REGEXP = "^(A|D|E|K|B1|B2|B3|B5|B6|B8|B9|B12|C)$";
    public static final String MINERAL_REGEXP = "^(CALCIUM|POTASSIUM|MAGNESIUM|IRON|PHOSPHORUS|SODIUM|ZINC|COPPER|MANGANESE|SELENIUM)$";
    public static final String FOOD_CATEGORY_REGEXP = "^(SWEETS|DAIRY_OR_EGGS|NUTS_AND_SEEDS|FRUITS_AND_VEGETABLES|WHOLE_GRAINS_AND_LEGUMES|OILS|OTHER)$";
}
