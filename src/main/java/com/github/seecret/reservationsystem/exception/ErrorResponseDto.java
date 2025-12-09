package com.github.seecret.reservationsystem.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(

        String message,

        String detailedMessage,

        LocalDateTime errorTime
) {
}
