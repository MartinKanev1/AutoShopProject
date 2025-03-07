package AutoShopProject.com.AutoShopProject.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;
import AutoShopProject.com.AutoShopProject.Mappers.CarDealershipMapper;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import AutoShopProject.com.AutoShopProject.Services.CarDealershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CarDealershipServiceTest {

    @Mock
    private CarDealershipRepository carDealershipRepository;

    @Mock
    private CarDealershipMapper carDealershipMapper;

    @InjectMocks
    private CarDealershipService carDealershipService;

    private CarDealerships dealership;
    private CarDealershipDTO dealershipDTO;

    @BeforeEach
    void setUp() {
        dealership = new CarDealerships();
        dealership.setDealershipId(1L);
        dealership.setName("Test Dealership");
        dealership.setDateOfCreation(Timestamp.valueOf(LocalDateTime.now()));
        dealership.setAddress("Test Address");

        dealershipDTO = new CarDealershipDTO(
                1L, "Test Dealership", Timestamp.valueOf(LocalDateTime.now()),
                "logo.jpg", "image/jpeg", new byte[0], "Test Address", 1L
        );
    }

    @Test
    void testFindAllCarDealerships() {
        when(carDealershipRepository.findAll()).thenReturn(List.of(dealership));
        when(carDealershipMapper.toDto(dealership)).thenReturn(dealershipDTO);

        List<CarDealershipDTO> result = carDealershipService.findAllCarDealerships();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Dealership", result.get(0).name());
    }

    @Test
    void testGetCarDealershipById_Exists() {
        when(carDealershipRepository.findById(1L)).thenReturn(Optional.of(dealership));
        when(carDealershipMapper.toDto(dealership)).thenReturn(dealershipDTO);

        CarDealershipDTO result = carDealershipService.getCarDealershipById(1L);

        assertNotNull(result);
        assertEquals(1L, result.dealershipId());
        assertEquals("Test Dealership", result.name());
    }

    @Test
    void testGetCarDealershipById_NotFound() {
        when(carDealershipRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                carDealershipService.getCarDealershipById(1L)
        );

        assertEquals("Dealership not found with ID: 1", exception.getMessage());
    }
}
