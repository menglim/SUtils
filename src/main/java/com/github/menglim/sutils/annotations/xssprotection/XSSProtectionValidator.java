package com.github.menglim.sutils.annotations.xssprotection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class XSSProtectionValidator implements ConstraintValidator<XSSProtection, String> {

    @Override
    public void initialize(XSSProtection constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotEmpty(value)) {
            return !isHtml(value);
        }
        return true;
    }

    public static boolean isHtml(String input) {
        boolean isHtml = false;
        if (input != null) {
            String htmlEscapedHtml = HtmlUtils.htmlEscape(input);
            if (!input.equals(htmlEscapedHtml)) {
                isHtml = true;
            }
        }
        return isHtml;
    }
}
