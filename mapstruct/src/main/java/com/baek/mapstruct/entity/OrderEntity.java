package com.baek.mapstruct.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class OrderEntity extends BaseEntity {
    private String orderName;
}
