package AutoShopProject.com.AutoShopProject.Repositories;

import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarDealershipRepository extends JpaRepository<CarDealerships, Long> {
    Optional<CarDealerships> findByName(String name);
}
