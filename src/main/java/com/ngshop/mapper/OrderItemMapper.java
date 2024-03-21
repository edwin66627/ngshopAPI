package com.ngshop.mapper;

import com.ngshop.dto.OrderItemDTO;
import com.ngshop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(uses = {ProductMapper.class}, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {
    OrderItem getOrderItem(OrderItemDTO orderItemDTO);
    @Named("WithoutOrder")
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", qualifiedByName = "WithoutCategoryDto")
    OrderItemDTO getOrderItemDtoWithoutOrder(OrderItem orderItem);

}
