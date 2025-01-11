package AutoShopProject.com.AutoShopProject.Repositories;

import AutoShopProject.com.AutoShopProject.Models.Favourites;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Long> {
    boolean existsByUserAndOffer(User user, Offers offer);

    Optional<Favourites> findByUserAndOffer(User user, Offers offer);

    List<Favourites> findAllByUser(User user);
}
