package AutoShopProject.com.AutoShopProject.Services;

import AutoShopProject.com.AutoShopProject.DTOs.FavouritesDTO;
import AutoShopProject.com.AutoShopProject.Models.Favourites;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.FavouritesRepository;
import AutoShopProject.com.AutoShopProject.Repositories.OffersRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final UserRepository userRepository;
    private final OffersRepository offersRepository;

    public FavouritesService(FavouritesRepository favouritesRepository,
                             UserRepository userRepository,
                             OffersRepository offersRepository) {
        this.favouritesRepository = favouritesRepository;
        this.userRepository = userRepository;
        this.offersRepository = offersRepository;
    }

    @Transactional
    public FavouritesDTO addFavourite(Long userId, Long offerId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));


        Offers offer = offersRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));


        boolean alreadyExists = favouritesRepository.existsByUserAndOffer(user, offer);
        if (alreadyExists) {
            throw new IllegalStateException("This offer is already in the user's favorites");
        }


        Favourites favourite = new Favourites();
        favourite.setUser(user);
        favourite.setOffer(offer);
        favourite.setAddedOn(Timestamp.valueOf(LocalDateTime.now()));

        Favourites savedFavourite = favouritesRepository.save(favourite);

        return new FavouritesDTO(savedFavourite.getId(), userId, offerId, savedFavourite.getAddedOn());
    }

    @Transactional
    public void removeFavourite(Long userId, Long offerId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));


        Offers offer = offersRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));


        Favourites favourite = favouritesRepository.findByUserAndOffer(user, offer)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found for user ID " + userId + " and offer ID " + offerId));

        favouritesRepository.delete(favourite);
    }


    public List<FavouritesDTO> getUserFavourites(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));


        List<Favourites> favourites = favouritesRepository.findAllByUser(user);

        return favourites.stream()
                .map(fav -> new FavouritesDTO(fav.getId(), userId, fav.getOffer().getOfferId(), fav.getAddedOn()))
                .collect(Collectors.toList());
    }
}

