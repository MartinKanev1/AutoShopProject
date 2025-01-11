package AutoShopProject.com.AutoShopProject.Mappers;
import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;

import AutoShopProject.com.AutoShopProject.Models.CarDealerships;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarDealershipMapper {


    CarDealershipDTO toDto(CarDealerships carDealership);

    @Mapping(target = "dealershipId", source = "dto.dealershipId")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "dateOfCreation", source = "dto.dateOfCreation")
    @Mapping(target = "logoImageName", source = "dto.logoImageName")
    @Mapping(target = "logoImageType", source = "dto.logoImageType")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "user.userId", source = "dto.userId")

    CarDealerships toEntity(CarDealershipDTO dto);
}

