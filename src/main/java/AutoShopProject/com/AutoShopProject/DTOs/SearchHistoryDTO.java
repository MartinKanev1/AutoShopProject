package AutoShopProject.com.AutoShopProject.DTOs;

import java.sql.Timestamp;

public record SearchHistoryDTO(
        Long id,
        Long userId,
        String searchCriteria,
        Timestamp searchedOn
) {
}
