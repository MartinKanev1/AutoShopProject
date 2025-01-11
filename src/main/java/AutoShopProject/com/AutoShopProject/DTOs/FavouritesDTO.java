package AutoShopProject.com.AutoShopProject.DTOs;

import java.sql.Timestamp;

public record FavouritesDTO(
        Long id,
        Long userId,
        Long offerId,
        Timestamp addedOn
) {
}
