package AutoShopProject.com.AutoShopProject.ControllersTests;

import AutoShopProject.com.AutoShopProject.Controllers.UserController;
import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO("John", "Doe", "john.doe@example.com", "password", "123456789", "Region", "City", null, null);
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\":\"John\"," +
                                "\"lastName\":\"Doe\"," +
                                "\"email\":\"john.doe@example.com\"," +
                                "\"password\":\"password\"," +
                                "\"phoneNumber\":\"123456789\"," +
                                "\"region\":\"Region\"," +
                                "\"city\":\"City\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));

        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    void testUpdateUser() throws Exception {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO("John", "Smith", "john.smith@example.com", "newpassword", "987654321", "New Region", "New City", null, null);
        when(userService.updateUser(eq(userId), any(UserDTO.class))).thenReturn(userDTO);

        // Act & Assert
        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\":\"John\"," +
                                "\"lastName\":\"Smith\"," +
                                "\"email\":\"john.smith@example.com\"," +
                                "\"password\":\"newpassword\"," +
                                "\"phoneNumber\":\"987654321\"," +
                                "\"region\":\"New Region\"," +
                                "\"city\":\"New City\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.email", is("john.smith@example.com")));

        verify(userService, times(1)).updateUser(eq(userId), any(UserDTO.class));
    }
}
