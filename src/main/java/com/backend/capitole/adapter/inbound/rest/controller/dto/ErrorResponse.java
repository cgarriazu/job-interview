package com.backend.capitole.adapter.inbound.rest.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private int status;
    private String error;
}
