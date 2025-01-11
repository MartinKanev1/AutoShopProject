package AutoShopProject.com.AutoShopProject.Controllers;

import AutoShopProject.com.AutoShopProject.DTOs.SearchHistoryDTO;
import AutoShopProject.com.AutoShopProject.Services.SearchHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search-history")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    public SearchHistoryController(SearchHistoryService searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    // Запазване на търсене
    @PostMapping
    public ResponseEntity<Void> saveSearchHistory(@RequestParam Long userId, @RequestParam String searchCriteria) {
        searchHistoryService.saveSearchHistory(userId, searchCriteria);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Извличане на последните 10 търсения
    @GetMapping("/{userId}")
    public ResponseEntity<List<SearchHistoryDTO>> getLast10Searches(@PathVariable Long userId) {
        List<SearchHistoryDTO> searchHistories = searchHistoryService.getLast10Searches(userId);
        return ResponseEntity.ok(searchHistories);
    }
}

