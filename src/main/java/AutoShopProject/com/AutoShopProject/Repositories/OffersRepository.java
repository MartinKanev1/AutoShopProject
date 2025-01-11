package AutoShopProject.com.AutoShopProject.Repositories;

import AutoShopProject.com.AutoShopProject.Models.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends JpaRepository<Offers,Long>, JpaSpecificationExecutor<Offers> {

}
