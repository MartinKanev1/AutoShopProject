package AutoShopProject.com.AutoShopProject.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.DTOs.FavouritesDTO;
import AutoShopProject.com.AutoShopProject.Models.Favourites;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.FavouritesRepository;
import AutoShopProject.com.AutoShopProject.Repositories.OffersRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.FavouritesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FavouritesServiceTest {

    @Mock
    private FavouritesRepository favouritesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OffersRepository offersRepository;

    @InjectMocks
    private FavouritesService favouritesService;

    private User user;
    private Offers offer;
    private Favourites favourite;
    private FavouritesDTO favouriteDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);

        offer = new Offers();
        offer.setOfferId(1L);

        favourite = new Favourites();
        favourite.setId(1L);
        favourite.setUser(user);
        favourite.setOffer(offer);
        favourite.setAddedOn(Timestamp.valueOf(LocalDateTime.now()));

        favouriteDTO = new FavouritesDTO(1L, 1L, 1L, favourite.getAddedOn());
    }

    @Test
    void testAddFavourite_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(favouritesRepository.existsByUserAndOffer(user, offer)).thenReturn(false);
        when(favouritesRepository.save(any(Favourites.class))).thenReturn(favourite);

        FavouritesDTO result = favouritesService.addFavourite(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.userId());
        assertEquals(1L, result.offerId());
    }

    @Test
    void testAddFavourite_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favouritesService.addFavourite(1L, 1L)
        );

        assertEquals("User with ID 1 not found", exception.getMessage());
    }

    @Test
    void testAddFavourite_OfferNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offersRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favouritesService.addFavourite(1L, 1L)
        );

        assertEquals("Offer with ID 1 not found", exception.getMessage());
    }

    @Test
    void testAddFavourite_AlreadyExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(favouritesRepository.existsByUserAndOffer(user, offer)).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                favouritesService.addFavourite(1L, 1L)
        );

        assertEquals("This offer is already in the user's favorites", exception.getMessage());
    }

    @Test
    void testRemoveFavourite_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(favouritesRepository.findByUserAndOffer(user, offer)).thenReturn(Optional.of(favourite));
        doNothing().when(favouritesRepository).delete(favourite);

        assertDoesNotThrow(() -> favouritesService.removeFavourite(1L, 1L));
        verify(favouritesRepository, times(1)).delete(favourite);
    }

    @Test
    void testRemoveFavourite_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(favouritesRepository.findByUserAndOffer(user, offer)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favouritesService.removeFavourite(1L, 1L)
        );

        assertEquals("Favorite not found for user ID 1 and offer ID 1", exception.getMessage());
    }

    @Test
    void testGetUserFavourites_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(favouritesRepository.findAllByUser(user)).thenReturn(List.of(favourite));

        List<FavouritesDTO> result = favouritesService.getUserFavourites(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).userId());
    }

    @Test
    void testGetUserFavourites_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                favouritesService.getUserFavourites(1L)
        );

        assertEquals("User with ID 1 not found", exception.getMessage());
    }
}
