package AutoShopProject.com.AutoShopProject.DTOs;

import java.sql.Timestamp;

public record ReportsDTO(
        Long reportId,
        Long userId,
        Long offerId,
        String reason,
        Timestamp reportedAt
) {
}
