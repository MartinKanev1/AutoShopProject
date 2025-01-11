package AutoShopProject.com.AutoShopProject.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {


    @Mapping(target = "firstName", source = "dto.firstName")
    @Mapping(target = "lastName", source = "dto.lastName")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "password", source = "dto.password")
    @Mapping(target = "phoneNumber", source = "dto.phoneNumber")
    @Mapping(target = "region", source = "dto.region")
    @Mapping(target = "city", source = "dto.city")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "carDealership", source = "dto.carDealership")

    User toEntity(UserDTO dto, Long id);


    UserDTO toDto(User user);
}


