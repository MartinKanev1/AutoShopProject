package AutoShopProject.com.AutoShopProject.Controllers;

import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;

import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.CarDealershipService;

import AutoShopProject.com.AutoShopProject.Services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cardealerships")
public class CarDealershipController {

    private final CarDealershipService carDealershipService;
    private final UserRepository userRepository;
    private final CarDealershipRepository carDealershipRepository;

    public CarDealershipController(CarDealershipService carDealershipService,UserRepository userRepository,CarDealershipRepository carDealershipRepository) {
        this.carDealershipService = carDealershipService;
        this.userRepository = userRepository;
        this.carDealershipRepository = carDealershipRepository;
    }

    @GetMapping
    public List<CarDealershipDTO> getAllCarDealerships() {
        return carDealershipService.findAllCarDealerships();
    }


    @GetMapping("/{dealershipId}")
    public ResponseEntity<CarDealershipDTO> getDealershipById(@PathVariable Long dealershipId) {
        CarDealershipDTO dealershipDTO = carDealershipService.getCarDealershipById(dealershipId);
        return ResponseEntity.ok(dealershipDTO);
    }


    @Transactional(readOnly = true)
    @GetMapping("/user/{userId}/logo")
    public ResponseEntity<byte[]> getCarDealershipLogoByUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CarDealerships dealership = user.getCarDealership();

        if (dealership == null || dealership.getLogoImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dealership.getLogoImageType()))
                .body(dealership.getLogoImageData());
    }

    @Transactional(readOnly = true)
    @GetMapping("/{dealershipId}/logo")
    public ResponseEntity<byte[]> getCarDealershipLogoByDealershipId(@PathVariable Long dealershipId) {
        CarDealerships dealership = carDealershipRepository.findById(dealershipId)
                .orElseThrow(() -> new RuntimeException("Dealership not found"));

        if (dealership.getLogoImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dealership.getLogoImageType()))
                .body(dealership.getLogoImageData());
    }




}
