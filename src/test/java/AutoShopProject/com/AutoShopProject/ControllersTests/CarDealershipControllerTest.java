package AutoShopProject.com.AutoShopProject.ControllersTests;

import AutoShopProject.com.AutoShopProject.Controllers.CarDealershipController;
import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;
import AutoShopProject.com.AutoShopProject.Services.CarDealershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarDealershipControllerTest {

    @Mock
    private CarDealershipService carDealershipService;

    @InjectMocks
    private CarDealershipController carDealershipController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carDealershipController).build();
    }

    @Test
    void testGetAllCarDealerships() throws Exception {
        // Arrange
        List<CarDealershipDTO> dealerships = List.of(
                new CarDealershipDTO(1L, "Dealership 1", null, "Logo1", "Type1", "Address1", 1L),
                new CarDealershipDTO(2L, "Dealership 2", null, "Logo2", "Type2", "Address2", 2L)
        );

        when(carDealershipService.findAllCarDealerships()).thenReturn(dealerships);

        // Act & Assert
        mockMvc.perform(get("/api/cardealerships")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Dealership 1")))
                .andExpect(jsonPath("$[1].name", is("Dealership 2")));

        verify(carDealershipService, times(1)).findAllCarDealerships();
    }

    @Test
    void testGetDealershipById() throws Exception {
        // Arrange
        Long dealershipId = 1L;
        CarDealershipDTO dealershipDTO = new CarDealershipDTO(dealershipId, "Dealership 1", null, "Logo1", "Type1", "Address1", 1L);

        when(carDealershipService.getCarDealershipById(dealershipId)).thenReturn(dealershipDTO);

        // Act & Assert
        mockMvc.perform(get("/api/cardealerships/{id}", dealershipId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dealership 1")))
                .andExpect(jsonPath("$.address", is("Address1")));

        verify(carDealershipService, times(1)).getCarDealershipById(dealershipId);
    }
}

