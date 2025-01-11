package AutoShopProject.com.AutoShopProject.Controllers;

import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;

import AutoShopProject.com.AutoShopProject.Services.CarDealershipService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cardealerships")
public class CarDealershipController {

    private final CarDealershipService carDealershipService;

    public CarDealershipController(CarDealershipService carDealershipService) {
        this.carDealershipService = carDealershipService;
    }

    @GetMapping
    public List<CarDealershipDTO> getAllCarDealerships() {
        return carDealershipService.findAllCarDealerships();
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<CarDealershipDTO> getDealershipById(@PathVariable Long dealershipId) {
        CarDealershipDTO dealershipDTO = carDealershipService.getCarDealershipById(dealershipId);
        return ResponseEntity.ok(dealershipDTO);
    }
    */
}
