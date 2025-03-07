package AutoShopProject.com.AutoShopProject.Repositories;

import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Long> {
    Optional<Object> findByOffer_OfferId(Long offerId);

    List<Reports> findAllByOffer(Offers offer);

    @Query("SELECT r.offer.offerId FROM Reports r WHERE r.reportId = :reportId")
    Long findOfferIdByReportId(Long reportId);
}
