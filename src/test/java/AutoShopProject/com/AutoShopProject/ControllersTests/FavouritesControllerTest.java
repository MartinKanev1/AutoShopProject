package AutoShopProject.com.AutoShopProject.ControllersTests;

import AutoShopProject.com.AutoShopProject.Controllers.FavouritesController;
import AutoShopProject.com.AutoShopProject.DTOs.FavouritesDTO;
import AutoShopProject.com.AutoShopProject.Services.FavouritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FavouritesControllerTest {

    @Mock
    private FavouritesService favouritesService;

    @InjectMocks
    private FavouritesController favouritesController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favouritesController).build();
    }

    @Test
    void testAddFavourite() throws Exception {

        Long userId = 1L;
        Long offerId = 2L;

        FavouritesDTO favouriteDTO = new FavouritesDTO(1L, userId, offerId, Timestamp.valueOf("2025-01-11 12:00:00"));
        when(favouritesService.addFavourite(userId, offerId)).thenReturn(favouriteDTO);


        mockMvc.perform(post("/api/favourites")
                        .param("userId", String.valueOf(userId))
                        .param("offerId", String.valueOf(offerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.offerId", is(2)));

        verify(favouritesService, times(1)).addFavourite(userId, offerId);
    }

    @Test
    void testRemoveFavourite() throws Exception {

        Long userId = 1L;
        Long offerId = 2L;

        doNothing().when(favouritesService).removeFavourite(userId, offerId);


        mockMvc.perform(delete("/api/favourites")
                        .param("userId", String.valueOf(userId))
                        .param("offerId", String.valueOf(offerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(favouritesService, times(1)).removeFavourite(userId, offerId);
    }

    @Test
    void testGetUserFavourites() throws Exception {

        Long userId = 1L;

        List<FavouritesDTO> favourites = List.of(
                new FavouritesDTO(1L, userId, 2L, Timestamp.valueOf("2025-01-11 12:00:00")),
                new FavouritesDTO(2L, userId, 3L, Timestamp.valueOf("2025-01-12 12:00:00"))
        );

        when(favouritesService.getUserFavourites(userId)).thenReturn(favourites);


        mockMvc.perform(get("/api/favourites/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

        verify(favouritesService, times(1)).getUserFavourites(userId);
    }
}

