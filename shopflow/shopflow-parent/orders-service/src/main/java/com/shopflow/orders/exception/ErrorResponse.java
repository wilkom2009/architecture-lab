package com.shopflow.orders.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private Instant timestamp;
    private Integer status;
    private String message;
    private List<String> errors;
    private String path;

}