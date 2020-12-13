/*
  User: rsopheak
  Date: 1/17/2019
  Time: 3:21 PM
*/

package com.github.menglim.sutils.annotations.doubleformat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class DoubleFormatValidator implements ConstraintValidator<DoubleFormat, Double> {

    private String format;
    private double min;
    private boolean round;

    @Override
    public void initialize(DoubleFormat doubleFormat) {
        format = doubleFormat.format();
        min = doubleFormat.min();
        round = doubleFormat.round();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        if (!format.isEmpty()) {
            switch (format) {
                case "#":
                    if (Objects.nonNull(value))
                        return value >= min;
                    else return true;
                case "#.#":
                    return true;
                case "#.##":
                    // "#.##" decimalPlaces = 2
                    if (Objects.nonNull(value)) {
                        if (value >= min) {
                            if (round) {
                                value = round(value);
                                return true;
                            } else {
                                String text = Double.toString(Math.abs(value));
                                int integerPlaces = text.indexOf('.');
                                int decimalPlaces = text.length() - integerPlaces - 1;
                                return decimalPlaces <= 2;
                            }
                        } else return false;
                    } else return true;
                default:
                    return false;
            }
        }
        return true;
    }

    private static double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
