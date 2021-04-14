package com.velheor.internship.mappers;

import com.velheor.internship.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressDTO {

    AddressDTO addressToAddressDTO(Address address);
}
