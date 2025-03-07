package AutoShopProject.com.AutoShopProject.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OffersMapper {


    OffersDTO toDTO(Offers offer);


    @Mapping(target = "offerId", source = "dto.offerId")
    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "postedOn", source = "dto.postedOn")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "category", source = "dto.category")
    @Mapping(target = "brand", source = "dto.brand")
    @Mapping(target = "model", source = "dto.model")
    @Mapping(target = "yearOfCreation", source = "dto.yearOfCreation")
    @Mapping(target = "fuel", source = "dto.fuel")
    @Mapping(target = "mileage", source = "dto.mileage")
    @Mapping(target = "color", source = "dto.color")
    @Mapping(target = "gear", source = "dto.gear")
    @Mapping(target = "power", source = "dto.power")
    @Mapping(target = "doorCount", source = "dto.doorCount")
    @Mapping(target = "imageName", source = "dto.ImageName")
    @Mapping(target = "imageType", source = "dto.ImageType")
    @Mapping(target = "imageData", source = "dto.ImageData")
    @Mapping(target = "user.userId", source = "dto.userId")


    Offers convertDtoToEntity(OffersDTO dto);
}
