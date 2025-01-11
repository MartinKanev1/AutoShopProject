package AutoShopProject.com.AutoShopProject.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import AutoShopProject.com.AutoShopProject.Models.Reports;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;

@Mapper
public interface ReportsMapper {



    ReportsDTO toDTO(Reports report);

    @Mapping(target = "reportId", source = "dto.reportId")
    @Mapping(target = "user.userId", source = "dto.userId")
    @Mapping(target = "offer.offerId", source = "dto.offerId")
    @Mapping(target = "reason", source = "dto.reason")
    @Mapping(target = "reportedAt", source = "dto.reportedAt")
    Reports toEntity(ReportsDTO dto);
}

