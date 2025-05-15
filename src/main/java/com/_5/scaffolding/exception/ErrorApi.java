package com._5.scaffolding.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorApi {
    private String timestamp;

    private Integer status;

    private String error;

    private String message;
}
