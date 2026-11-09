package com.github.mateusgromowski.supporthub.handler;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime timestamp, String message, String detail) {
}
