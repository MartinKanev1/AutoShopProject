package AutoShopProject.com.AutoShopProject.Controllers;

import AutoShopProject.com.AutoShopProject.DTOs.FavouritesDTO;
import AutoShopProject.com.AutoShopProject.Services.FavouritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
public class FavouritesController {

    private final FavouritesService favouritesService;

    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }


    @PostMapping
    public ResponseEntity<FavouritesDTO> addFavourite(@RequestParam Long userId, @RequestParam Long offerId) {
        FavouritesDTO favourite = favouritesService.addFavourite(userId, offerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(favourite);
    }



    @DeleteMapping
    public ResponseEntity<Void> removeFavourite(@RequestParam Long userId, @RequestParam Long offerId) {
        favouritesService.removeFavourite(userId, offerId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<FavouritesDTO>> getUserFavourites(@PathVariable Long userId) {
        List<FavouritesDTO> favourites = favouritesService.getUserFavourites(userId);
        return ResponseEntity.ok(favourites);
    }
}

