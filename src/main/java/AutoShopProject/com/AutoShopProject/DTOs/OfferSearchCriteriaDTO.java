package AutoShopProject.com.AutoShopProject.DTOs;

import java.math.BigDecimal;

public record OfferSearchCriteriaDTO(
        String brand,
        String model,
        String fuel,
        String gear,
        String color,
        Integer doorCount,
        Boolean isDealer,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        BigDecimal minMileage,
        BigDecimal maxMileage,
        Integer minPower,
        Integer maxPower,
        Integer minYear,
        Integer maxYear
) {


}
