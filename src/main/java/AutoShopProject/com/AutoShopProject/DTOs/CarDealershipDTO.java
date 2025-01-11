package AutoShopProject.com.AutoShopProject.DTOs;

import java.sql.Timestamp;

public record CarDealershipDTO(
        Long dealershipId,
        String name,
        Timestamp dateOfCreation,
        String logoImageName,
        String logoImageType,
        String address,
        Long userId

) {
}
