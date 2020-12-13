package com.github.menglim.sutils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
public class ApiErrorDetail {

    private Date timestamp;
    private String responseMessage;
    private int responseCode;

}
