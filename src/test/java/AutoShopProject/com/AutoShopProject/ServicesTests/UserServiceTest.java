package AutoShopProject.com.AutoShopProject.ServicesTests;

import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;
import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Mappers.CarDealershipMapper;
import AutoShopProject.com.AutoShopProject.Mappers.UserMapper;
import AutoShopProject.com.AutoShopProject.Models.SellerType;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarDealershipRepository carDealershipRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CarDealershipMapper carDealershipMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_UserExists() {

        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setFirstName("John");

        UserDTO userDTO = new UserDTO(
                "John",
                "Doe",
                "john@example.com",
                "password",
                "12345",
                "Region",
                "City",
                SellerType.PRIVATE_SELLER,
                null
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);


        Optional<UserDTO> result = userService.getUserById(userId);


        assertTrue(result.isPresent());
        assertEquals("John", result.get().firstName());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void testGetUserById_UserNotFound() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        Optional<UserDTO> result = userService.getUserById(userId);


        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).toDto(any(User.class));
    }

    @Test
    void testFindAllUsers() {

        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(1L);
        user1.setFirstName("John");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setFirstName("Jane");

        users.add(user1);
        users.add(user2);

        List<UserDTO> userDTOs = List.of(
                new UserDTO("John", "Doe", "john@example.com", "password", "12345", "Region", "City", SellerType.PRIVATE_SELLER, null),
                new UserDTO("Jane", "Smith", "jane@example.com", "password", "54321", "Region", "City", SellerType.PRIVATE_SELLER, null)
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user1)).thenReturn(userDTOs.get(0));
        when(userMapper.toDto(user2)).thenReturn(userDTOs.get(1));


        List<UserDTO> result = userService.findAllUsers();


        assertEquals(2, result.size());
        assertEquals("John", result.get(0).firstName());
        assertEquals("Jane", result.get(1).firstName());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    void testCreateUser() {

        CarDealershipDTO carDealershipDTO = new CarDealershipDTO(1L, "Dealership Name", null, "LogoImageName", "LogoImageType", "Address", 1L);
        UserDTO userDTO = new UserDTO("John", "Doe", "john@example.com", "password", "12345", "Region", "City", SellerType.CAR_DEALERSHIP, carDealershipDTO);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setFirstName("John");
        savedUser.setLastName("Doe");
        savedUser.setEmail("john@example.com");

        UserDTO createdUserDTO = new UserDTO("John", "Doe", "john@example.com", "password", "12345", "Region", "City", SellerType.CAR_DEALERSHIP, carDealershipDTO);

        when(userMapper.toEntity(userDTO, null)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(createdUserDTO);


        UserDTO result = userService.createUser(userDTO);


        assertNotNull(result);
        assertEquals("John", result.firstName());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toEntity(userDTO, null);
        verify(userMapper, times(1)).toDto(savedUser);
    }

    @Test
    void testDeleteUser() {

        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        userService.deleteUser(userId);


        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }
}

