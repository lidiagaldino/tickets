package com.lidiagaldino.tickets.infraestructure.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lidiagaldino.tickets.infraestructure.exceptions.BaseCustomException;

public record ErrorDTO(
        @JsonProperty("message")
        String message,
        @JsonProperty("cause")
        String cause
) {
    public ErrorDTO(BaseCustomException baseCustomException) {
        this(baseCustomException.getMessage(), baseCustomException.getErrorCode());
    }
}
