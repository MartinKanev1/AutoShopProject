package AutoShopProject.com.AutoShopProject.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import AutoShopProject.com.AutoShopProject.Models.SearchHistory;
import AutoShopProject.com.AutoShopProject.DTOs.SearchHistoryDTO;

@Mapper
public interface SearchHistoryMapper {




    SearchHistoryDTO toDTO(SearchHistory searchHistory);


    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "user.userId", source = "dto.userId")
    @Mapping(target = "searchedOn", source = "dto.searchedOn")
    @Mapping(target = "searchCriteria", source = "dto.searchCriteria")
    SearchHistory convertDtoToEntity(SearchHistoryDTO dto);
}

