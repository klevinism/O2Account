package com.o2dent.lib.accounts.helpers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.util.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidURL, String> {

    private Pattern pattern;
    private Matcher matcher;
    //private static final String URL_PATTERN = "(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?"; // GENERAL URL REGEX (advanced)
    private static final String URL_PATTERN = "(http:\\/\\/|https:\\/\\/)?(?:[\\w-]+\\.)*([\\w-]{1,63})(?:\\.(?:\\w{3}|\\w{2}))(?:$|\\/)"; // SIMPLE DOMAIN REGEX

    @Override
    public void initialize(ValidURL constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) { return (validateEmail(email)); }

    private boolean validateEmail(String url) {
        pattern = Pattern.compile(URL_PATTERN);
        matcher = pattern.matcher(url);
        return matcher.matches() || Strings.isBlank(url);
    }
}