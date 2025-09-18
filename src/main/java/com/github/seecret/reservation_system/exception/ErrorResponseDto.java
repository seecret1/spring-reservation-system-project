package com.github.seecret.reservation_system.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(

        String message,

        String detailedMessage,

        LocalDateTime errorTime
) {
}
