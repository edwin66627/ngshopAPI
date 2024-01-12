package com.ngshop.mapper;

import com.ngshop.dto.OrderDTO;
import com.ngshop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(uses = {UserMapper.class, OrderItemMapper.class}, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    Order getOrder(OrderDTO orderDTO);
    @Mapping(target = "user", qualifiedByName = "WithoutRoles")
    Order getOrderWithoutUserRoles(OrderDTO orderDTO);
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "orderItems", qualifiedByName = "WithoutOrder")
    OrderDTO getOrderDtoWithNoUserNoAddress(Order order);

    @Mapping(target = "orderItems", qualifiedByName = "WithoutOrder")
    OrderDTO getOrderDto(Order order);
}
