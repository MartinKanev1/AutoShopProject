package AutoShopProject.com.AutoShopProject.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Mappers.CarDealershipMapper;
import AutoShopProject.com.AutoShopProject.Mappers.OffersMapper;
import AutoShopProject.com.AutoShopProject.Mappers.UserMapper;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.Roles;
import AutoShopProject.com.AutoShopProject.Models.SellerType;
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

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarDealershipRepository carDealershipRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CarDealershipMapper carDealershipMapper;

    @Mock
    private OffersMapper offersMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("securepassword");
        user.setPhoneNumber("123456789");
        user.setRegion("Sofia");
        user.setCity("Sofia");
        user.setType(SellerType.PRIVATE_SELLER);
        user.setRole(Roles.Dealer);

        userDTO = new UserDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "securepassword",
                "123456789",
                "Sofia",
                "Sofia",
                SellerType.PRIVATE_SELLER,
                Roles.Dealer,
                null // No CarDealership for this test case
        );
    }


    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        Optional<UserDTO> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().firstName());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.getUserById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetUserIdFromEmail_UserExists() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        Long userId = userService.getUserIdFromEmail("john.doe@example.com");

        assertEquals(1L, userId);
    }

    @Test
    void testGetUserIdFromEmail_UserNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserIdFromEmail("unknown@example.com");
        });

        assertEquals("User not found for email: unknown@example.com", exception.getMessage());
    }

    @Test
    void testFindAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        List<UserDTO> users = userService.findAllUsers();

        assertEquals(1, users.size());
        assertEquals("John", users.get(0).firstName());
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals("John", createdUser.firstName());
    }

    @Test
    void testDeleteUser_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));

        assertEquals("User not found with ID: 1", exception.getMessage());
    }
}
