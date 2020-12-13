package com.github.menglim.sutils.annotations.ajaxrequest;


import com.github.menglim.sutils.SUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AjaxRequestValidator implements ConstraintValidator<AjaxRequest, HttpServletRequest> {
    @Override
    public boolean isValid(HttpServletRequest httpServletRequest, ConstraintValidatorContext constraintValidatorContext) {
        return SUtils.getInstance().isAjaxRequest(httpServletRequest);
    }
}
