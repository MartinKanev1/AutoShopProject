package AutoShopProject.com.AutoShopProject.Controllers;

import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Services.CarDealershipService;
import AutoShopProject.com.AutoShopProject.Services.OffersService;
import AutoShopProject.com.AutoShopProject.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final OffersService offerService;
    private final CarDealershipService carDealershipService;

    public AdminController(UserService userService, OffersService offerService, CarDealershipService carDealershipService) {
        this.userService = userService;
        this.offerService = offerService;
        this.carDealershipService = carDealershipService;
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/dealerships/{id}")
    public ResponseEntity<CarDealershipDTO> getDealershipById(@PathVariable("id") Long dealershipId) {
        CarDealershipDTO dealershipDTO = carDealershipService.getCarDealershipById(dealershipId);
        return ResponseEntity.ok(dealershipDTO);
    }


    @GetMapping("/offers/{id}/reports")
    public ResponseEntity<List<ReportsDTO>> getReportsForOffer(@PathVariable Long id) {
        List<ReportsDTO> reports = offerService.getReportsForOffer(id);
        return ResponseEntity.ok(reports);
    }
}

