package AutoShopProject.com.AutoShopProject.Controllers;


import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CarDealershipRepository carDealershipRepository;

    public UserController(UserService userService,UserRepository userRepository,CarDealershipRepository carDealershipRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.carDealershipRepository = carDealershipRepository;
    }

    // Вземане на потребител по ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/id-by-email/{email}")
    public ResponseEntity<?> getUserIdByEmail(@PathVariable String email) {
        try {
            Long userId = userService.getUserIdFromEmail(email);
            return ResponseEntity.ok(Collections.singletonMap("userId", userId)); // Return as JSON object
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for email: " + email);
        }
    }



    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestPart UserDTO userDTO, @RequestPart(value = "logoImage", required = false) MultipartFile logoImage) throws IOException {
        UserDTO updatedUser = userService.updateUser(id, userDTO, logoImage);
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/dealership/{dealershipId}/userId")
    public ResponseEntity<Long> getUserIdByDealershipId(@PathVariable Long dealershipId) {
        CarDealerships dealership = carDealershipRepository.findById(dealershipId)
                .orElseThrow(() -> new RuntimeException("Dealership not found"));

        User user = dealership.getUser();

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getUserId());
    }

    @GetMapping("/{userId}/dealershipId")
    public ResponseEntity<Long> getDealershipIdbyUserId(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        CarDealerships carDealerships = user.getCarDealership();

        if (carDealerships == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(carDealerships.getDealershipId());
    }


    @GetMapping("/{userId}/offers")
    public ResponseEntity<List<OffersDTO>> getUserOffers(@PathVariable Long userId) {
        List<OffersDTO> offers = userService.getOffersByUserId(userId);
        return ResponseEntity.ok(offers);
    }






}

