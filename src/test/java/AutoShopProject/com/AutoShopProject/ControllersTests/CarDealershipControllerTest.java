package AutoShopProject.com.AutoShopProject.ControllersTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.Controllers.CarDealershipController;
import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.CarDealershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarDealershipControllerTest {

    @Mock
    private CarDealershipService carDealershipService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarDealershipRepository carDealershipRepository;

    @InjectMocks
    private CarDealershipController carDealershipController;

    private CarDealershipDTO dealershipDTO;
    private CarDealerships dealership;
    private User user;

    @BeforeEach
    void setUp() {
        dealership = new CarDealerships();
        dealership.setDealershipId(1L);
        dealership.setName("Test Dealership");
        dealership.setDateOfCreation(Timestamp.valueOf(LocalDateTime.now()));
        dealership.setAddress("Test Address");
        dealership.setLogoImageType("image/jpeg");
        dealership.setLogoImageData(new byte[]{1, 2, 3});

        user = new User();
        user.setUserId(1L);
        user.setCarDealership(dealership);

        dealershipDTO = new CarDealershipDTO(1L, "Test Dealership", Timestamp.valueOf(LocalDateTime.now()),
                "logo.jpg", "image/jpeg", new byte[]{1, 2, 3}, "Test Address", 1L);
    }

    @Test
    void testGetAllCarDealerships() {
        when(carDealershipService.findAllCarDealerships()).thenReturn(List.of(dealershipDTO));

        List<CarDealershipDTO> response = carDealershipController.getAllCarDealerships();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Test Dealership", response.get(0).name());
    }

    @Test
    void testGetDealershipById_Exists() {
        when(carDealershipService.getCarDealershipById(1L)).thenReturn(dealershipDTO);

        ResponseEntity<CarDealershipDTO> response = carDealershipController.getDealershipById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Dealership", response.getBody().name());
    }

    @Test
    void testGetCarDealershipLogoByUserId_Exists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<byte[]> response = carDealershipController.getCarDealershipLogoByUserId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(new byte[]{1, 2, 3}, response.getBody());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
    }

    @Test
    void testGetCarDealershipLogoByUserId_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> carDealershipController.getCarDealershipLogoByUserId(1L));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetCarDealershipLogoByDealershipId_Exists() {
        when(carDealershipRepository.findById(1L)).thenReturn(Optional.of(dealership));

        ResponseEntity<byte[]> response = carDealershipController.getCarDealershipLogoByDealershipId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(new byte[]{1, 2, 3}, response.getBody());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
    }

    @Test
    void testGetCarDealershipLogoByDealershipId_NotFound() {
        when(carDealershipRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> carDealershipController.getCarDealershipLogoByDealershipId(1L));
        assertEquals("Dealership not found", exception.getMessage());
    }
}
