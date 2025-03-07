package AutoShopProject.com.AutoShopProject.ControllersTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.Controllers.SearchHistoryController;
import AutoShopProject.com.AutoShopProject.DTOs.SearchHistoryDTO;
import AutoShopProject.com.AutoShopProject.Services.SearchHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SearchHistoryControllerTest {

    @Mock
    private SearchHistoryService searchHistoryService;

    @InjectMocks
    private SearchHistoryController searchHistoryController;

    private SearchHistoryDTO searchHistoryDTO;

    @BeforeEach
    void setUp() {
        searchHistoryDTO = new SearchHistoryDTO(1L, 1L, "Sample Search Criteria", Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void testSaveSearchHistory_Success() {
        doNothing().when(searchHistoryService).saveSearchHistory(1L, "Sample Search Criteria");

        ResponseEntity<Void> response = searchHistoryController.saveSearchHistory(1L, "Sample Search Criteria");

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testGetLast10Searches_Success() {
        List<SearchHistoryDTO> searchHistories = List.of(searchHistoryDTO);
        when(searchHistoryService.getLast10Searches(1L)).thenReturn(searchHistories);

        ResponseEntity<List<SearchHistoryDTO>> response = searchHistoryController.getLast10Searches(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(searchHistories, response.getBody());
    }
}
