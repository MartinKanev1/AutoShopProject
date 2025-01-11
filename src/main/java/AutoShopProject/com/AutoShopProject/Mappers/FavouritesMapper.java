package AutoShopProject.com.AutoShopProject.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import AutoShopProject.com.AutoShopProject.Models.Favourites;
import AutoShopProject.com.AutoShopProject.DTOs.FavouritesDTO;

@Mapper
public interface FavouritesMapper {




    FavouritesDTO toDTO(Favourites favourite);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "user.userId", source = "dto.userId")
    @Mapping(target = "offer.offerId", source = "dto.offerId")
    @Mapping(target = "addedOn", source = "dto.addedOn")
    Favourites convertDtoToEntity(FavouritesDTO dto);
}

