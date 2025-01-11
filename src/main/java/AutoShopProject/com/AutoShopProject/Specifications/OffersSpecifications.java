package AutoShopProject.com.AutoShopProject.Specifications;

import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.SellerType;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class OffersSpecifications {

    public static Specification<Offers> hasBrand(String brand) {
        return (root, query, criteriaBuilder) ->
                brand != null ? criteriaBuilder.equal(root.get("brand"), brand) : null;
    }

    public static Specification<Offers> hasModel(String model) {
        return (root, query, criteriaBuilder) ->
                model != null ? criteriaBuilder.equal(root.get("model"), model) : null;
    }

    public static Specification<Offers> hasFuel(String fuel) {
        return (root, query, criteriaBuilder) ->
                fuel != null ? criteriaBuilder.equal(root.get("fuel"), fuel) : null;
    }

    public static Specification<Offers> hasGear(String gear) {
        return (root, query, criteriaBuilder) ->
                gear != null ? criteriaBuilder.equal(root.get("gear"), gear) : null;
    }

    public static Specification<Offers> hasColor(String color) {
        return (root, query, criteriaBuilder) ->
                color != null ? criteriaBuilder.equal(root.get("color"), color) : null;
    }

    public static Specification<Offers> hasDoors(Integer doors) {
        return (root, query, criteriaBuilder) ->
                doors != null ? criteriaBuilder.equal(root.get("doorCount"), doors) : null;
    }

    public static Specification<Offers> isByDealer(boolean isDealer) {
        return (root, query, criteriaBuilder) -> {
            if (isDealer) {
                return criteriaBuilder.equal(root.get("user").get("type"), SellerType.CAR_DEALERSHIP);
            } else {
                return criteriaBuilder.equal(root.get("user").get("type"), SellerType.PRIVATE_SELLER);
            }
        };
    }

    public static Specification<Offers> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            return null;
        };
    }

    public static Specification<Offers> mileageBetween(BigDecimal minMileage, BigDecimal maxMileage) {
        return (root, query, criteriaBuilder) -> {
            if (minMileage != null && maxMileage != null) {
                return criteriaBuilder.between(root.get("mileage"), minMileage, maxMileage);
            } else if (minMileage != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("mileage"), minMileage);
            } else if (maxMileage != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("mileage"), maxMileage);
            }
            return null;
        };
    }

    public static Specification<Offers> powerBetween(Integer minPower, Integer maxPower) {
        return (root, query, criteriaBuilder) -> {
            if (minPower != null && maxPower != null) {
                return criteriaBuilder.between(root.get("power"), minPower, maxPower);
            } else if (minPower != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("power"), minPower);
            } else if (maxPower != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("power"), maxPower);
            }
            return null;
        };
    }

    public static Specification<Offers> yearBetween(Integer minYear, Integer maxYear) {
        return (root, query, criteriaBuilder) -> {
            if (minYear != null && maxYear != null) {
                return criteriaBuilder.between(root.get("yearOfCreation"), minYear, maxYear);
            } else if (minYear != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("yearOfCreation"), minYear);
            } else if (maxYear != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("yearOfCreation"), maxYear);
            }
            return null;
        };
    }
}
