package AutoShopProject.com.AutoShopProject.DTOs;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record OffersDTO(
        Long offerId,
        String title,
        String description,
        String status,
        Timestamp postedOn,
        BigDecimal price,
        String brand,
        String model,
        Integer yearOfCreation,
        String fuel,
        BigDecimal mileage,
        String color,
        String gear,
        Integer power,
        Integer doorCount,

        UserDTO user
) {
}
