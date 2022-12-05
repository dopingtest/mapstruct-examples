package com.baek.mapstruct.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class BaseEntity {
    private LocalDateTime createdAt;
}
