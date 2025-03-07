package AutoShopProject.com.AutoShopProject.Repositories;

import AutoShopProject.com.AutoShopProject.Models.SearchHistory;
import AutoShopProject.com.AutoShopProject.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findTop10ByUserOrderBySearchedOnDesc(User user);

    //boolean existsByUserIdAndSearchCriteria(Long userId, String searchCriteriaJson);
}
