package AutoShopProject.com.AutoShopProject.ControllersTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.Controllers.UserController;
import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarDealershipRepository carDealershipRepository;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);

        userDTO = new UserDTO("John", "Doe", "john.doe@example.com", "password",
                "123456789", "Sofia", "Sofia", null, null, null);
    }

    @Test
    void testGetUserById_UserExists() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userDTO));

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().firstName());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateUser_Success() {
        when(userService.createUser(userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("John", response.getBody().firstName());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testGetUserIdByEmail_UserExists() {
        when(userService.getUserIdFromEmail("john.doe@example.com")).thenReturn(1L);

        ResponseEntity<?> response = userController.getUserIdByEmail("john.doe@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, ((java.util.Map<?, ?>) response.getBody()).get("userId"));
    }

    @Test
    void testGetUserIdByEmail_UserNotFound() {
        when(userService.getUserIdFromEmail("unknown@example.com")).thenThrow(new RuntimeException("User not found for email: unknown@example.com"));

        ResponseEntity<?> response = userController.getUserIdByEmail("unknown@example.com");

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found for email: unknown@example.com", response.getBody());
    }

    @Test
    void testUpdateUser_Success() throws IOException {
        MultipartFile image = mock(MultipartFile.class);
        when(userService.updateUser(1L, userDTO, image)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(1L, userDTO, image);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().firstName());
    }

    @Test
    void testGetUserIdByDealershipId_DealershipExists() {
        CarDealerships dealership = new CarDealerships();
        dealership.setDealershipId(1L);
        dealership.setUser(user);

        when(carDealershipRepository.findById(1L)).thenReturn(Optional.of(dealership));

        ResponseEntity<Long> response = userController.getUserIdByDealershipId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testGetUserIdByDealershipId_DealershipNotFound() {
        when(carDealershipRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userController.getUserIdByDealershipId(1L));
        assertEquals("Dealership not found", exception.getMessage());
    }

    @Test
    void testGetDealershipIdByUserId_UserExists() {
        CarDealerships dealership = new CarDealerships();
        dealership.setDealershipId(1L);
        user.setCarDealership(dealership);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<Long> response = userController.getDealershipIdbyUserId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testGetDealershipIdByUserId_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userController.getDealershipIdbyUserId(1L));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetUserOffers() {
        List<OffersDTO> offers = List.of(mock(OffersDTO.class));
        when(userService.getOffersByUserId(1L)).thenReturn(offers);

        ResponseEntity<List<OffersDTO>> response = userController.getUserOffers(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(offers, response.getBody());
    }
}
