package AutoShopProject.com.AutoShopProject.ControllersTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.Controllers.FavouritesController;
import AutoShopProject.com.AutoShopProject.DTOs.FavouritesDTO;
import AutoShopProject.com.AutoShopProject.Services.FavouritesService;
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
class FavouritesControllerTest {

    @Mock
    private FavouritesService favouritesService;

    @InjectMocks
    private FavouritesController favouritesController;

    private FavouritesDTO favouriteDTO;

    @BeforeEach
    void setUp() {
        favouriteDTO = new FavouritesDTO(1L, 1L, 1L, Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void testAddFavourite_Success() {
        when(favouritesService.addFavourite(1L, 1L)).thenReturn(favouriteDTO);

        ResponseEntity<FavouritesDTO> response = favouritesController.addFavourite(1L, 1L);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().userId());
        assertEquals(1L, response.getBody().offerId());
    }

    @Test
    void testRemoveFavourite_Success() {
        doNothing().when(favouritesService).removeFavourite(1L, 1L);

        ResponseEntity<Void> response = favouritesController.removeFavourite(1L, 1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testGetUserFavourites_Success() {
        List<FavouritesDTO> favourites = List.of(favouriteDTO);
        when(favouritesService.getUserFavourites(1L)).thenReturn(favourites);

        ResponseEntity<List<FavouritesDTO>> response = favouritesController.getUserFavourites(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(favourites, response.getBody());
    }
}
