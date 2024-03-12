package com.ngshop.mapper;

import com.ngshop.dto.security.AddressDTO;
import com.ngshop.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address getAddress(AddressDTO addressDTO);
    AddressDTO getAddressDto(Address address);
}
