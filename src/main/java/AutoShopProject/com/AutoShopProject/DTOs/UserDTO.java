package AutoShopProject.com.AutoShopProject.DTOs;

import AutoShopProject.com.AutoShopProject.Models.Roles;
import AutoShopProject.com.AutoShopProject.Models.SellerType;

public record UserDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        String region,
        String city,
        SellerType type,
        Roles role,
        CarDealershipDTO carDealership
) {
}
