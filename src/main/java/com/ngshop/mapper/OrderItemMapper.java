package com.ngshop.mapper;

import com.ngshop.dto.OrderItemDTO;
import com.ngshop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {
    OrderItem getOrderItem(OrderItemDTO orderItemDTO);
    @Named("WithoutOrder")
    @Mapping(target = "order", ignore = true)
    OrderItemDTO getOrderItemDtoWithoutOrder(OrderItem orderItem);

}
