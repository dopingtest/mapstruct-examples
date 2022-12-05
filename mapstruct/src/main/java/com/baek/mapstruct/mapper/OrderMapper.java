package com.baek.mapstruct.mapper;

import com.baek.mapstruct.dto.OrderDto;
import com.baek.mapstruct.entity.OrderEntity;
import com.baek.mapstruct.entity.OrderInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    @Mappings(value = @Mapping(source = "orderedAt", target = "orderedAt", dateFormat = "yyyy-MM-dd HH:mm:ss"))
    OrderDto.Main of(OrderInfo.Main mainResult);

    List<OrderDto.Main> of(List<OrderInfo.Main> list);

    OrderDto.OrderResponse of(OrderEntity entity);
}
