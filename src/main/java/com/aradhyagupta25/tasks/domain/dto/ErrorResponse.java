package com.aradhyagupta25.tasks.domain.dto;

public record ErrorResponse(
        int status,     // http status code return
        String messege,     // helps to identify the type of error.
        String details
) {
}
