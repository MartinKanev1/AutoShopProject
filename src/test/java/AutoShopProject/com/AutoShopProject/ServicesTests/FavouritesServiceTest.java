package AutoShopProject.com.AutoShopProject.ServicesTests;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavouritesServiceTest {

    @Mock
    private FavouritesRepository favouritesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OffersRepository offersRepository;

    @InjectMocks
    private FavouritesService favouritesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFavourite() {

        Long userId = 1L;
        Long offerId = 1L;

        User user = new User();
        user.setUserId(userId);

        Offers offer = new Offers();
        offer.setOfferId(offerId);

        Favourites favourite = new Favourites();
        favourite.setId(1L);
        favourite.setUser(user);
        favourite.setOffer(offer);
        favourite.setAddedOn(Timestamp.valueOf(LocalDateTime.now()));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(offersRepository.findById(offerId)).thenReturn(Optional.of(offer));
        when(favouritesRepository.existsByUserAndOffer(user, offer)).thenReturn(false);
        when(favouritesRepository.save(any(Favourites.class))).thenReturn(favourite);


        FavouritesDTO result = favouritesService.addFavourite(userId, offerId);


        assertNotNull(result);
        assertEquals(userId, result.userId());
        assertEquals(offerId, result.offerId());
        verify(userRepository, times(1)).findById(userId);
        verify(offersRepository, times(1)).findById(offerId);
        verify(favouritesRepository, times(1)).existsByUserAndOffer(user, offer);
        verify(favouritesRepository, times(1)).save(any(Favourites.class));
    }

    @Test
    void testRemoveFavourite() {

        Long userId = 1L;
        Long offerId = 1L;

        User user = new User();
        user.setUserId(userId);

        Offers offer = new Offers();
        offer.setOfferId(offerId);

        Favourites favourite = new Favourites();
        favourite.setId(1L);
        favourite.setUser(user);
        favourite.setOffer(offer);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(offersRepository.findById(offerId)).thenReturn(Optional.of(offer));
        when(favouritesRepository.findByUserAndOffer(user, offer)).thenReturn(Optional.of(favourite));


        favouritesService.removeFavourite(userId, offerId);


        verify(userRepository, times(1)).findById(userId);
        verify(offersRepository, times(1)).findById(offerId);
        verify(favouritesRepository, times(1)).findByUserAndOffer(user, offer);
        verify(favouritesRepository, times(1)).delete(favourite);
    }

    @Test
    void testGetUserFavourites() {

        Long userId = 1L;

        User user = new User();
        user.setUserId(userId);

        Offers offer1 = new Offers();
        offer1.setOfferId(1L);

        Offers offer2 = new Offers();
        offer2.setOfferId(2L);

        Favourites favourite1 = new Favourites();
        favourite1.setId(1L);
        favourite1.setUser(user);
        favourite1.setOffer(offer1);
        favourite1.setAddedOn(Timestamp.valueOf(LocalDateTime.now()));

        Favourites favourite2 = new Favourites();
        favourite2.setId(2L);
        favourite2.setUser(user);
        favourite2.setOffer(offer2);
        favourite2.setAddedOn(Timestamp.valueOf(LocalDateTime.now()));

        List<Favourites> favourites = new ArrayList<>();
        favourites.add(favourite1);
        favourites.add(favourite2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(favouritesRepository.findAllByUser(user)).thenReturn(favourites);


        List<FavouritesDTO> result = favouritesService.getUserFavourites(userId);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).offerId());
        assertEquals(2L, result.get(1).offerId());
        verify(userRepository, times(1)).findById(userId);
        verify(favouritesRepository, times(1)).findAllByUser(user);
    }
}

