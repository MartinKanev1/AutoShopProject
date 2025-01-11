package AutoShopProject.com.AutoShopProject.Services;

import AutoShopProject.com.AutoShopProject.DTOs.SearchHistoryDTO;
import AutoShopProject.com.AutoShopProject.Models.SearchHistory;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.SearchHistoryRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserRepository userRepository;

    public SearchHistoryService(SearchHistoryRepository searchHistoryRepository, UserRepository userRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
        this.userRepository = userRepository;
    }


    public void saveSearchHistory(Long userId, String searchCriteria) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));


        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setSearchedOn(Timestamp.valueOf(LocalDateTime.now()));
        searchHistory.setUser(user);
        searchHistory.setSearchCriteria(searchCriteria);


        searchHistoryRepository.save(searchHistory);
    }


    public List<SearchHistoryDTO> getLast10Searches(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));


        List<SearchHistory> searchHistories = searchHistoryRepository.findTop10ByUserOrderBySearchedOnDesc(user);

        return searchHistories.stream()
                .map(history -> new SearchHistoryDTO(
                        history.getId(),
                        userId,
                        history.getSearchCriteria(),
                        history.getSearchedOn()
                ))
                .collect(Collectors.toList());
    }
}
