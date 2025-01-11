package AutoShopProject.com.AutoShopProject.ServicesTests;

import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;
import AutoShopProject.com.AutoShopProject.Mappers.CarDealershipMapper;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import AutoShopProject.com.AutoShopProject.Services.CarDealershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class CarDealershipServiceTest {
    @Mock
    private CarDealershipRepository carDealershipRepository;

    @Mock
    private CarDealershipMapper carDealershipMapper;

    @InjectMocks
    private CarDealershipService carDealershipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCarDealerships() {
        // Arrange
        List<CarDealerships> carDealerships = new ArrayList<>();
        CarDealerships dealership1 = new CarDealerships();
        dealership1.setDealershipId(1L);
        dealership1.setName("Dealership 1");

        CarDealerships dealership2 = new CarDealerships();
        dealership2.setDealershipId(2L);
        dealership2.setName("Dealership 2");

        carDealerships.add(dealership1);
        carDealerships.add(dealership2);

        List<CarDealershipDTO> dealershipDTOs = List.of(
                new CarDealershipDTO(1L, "Dealership 1", null, "Logo1", "Type1", "Address1", 1L),
                new CarDealershipDTO(2L, "Dealership 2", null, "Logo2", "Type2", "Address2", 2L)
        );

        when(carDealershipRepository.findAll()).thenReturn(carDealerships);
        when(carDealershipMapper.toDto(dealership1)).thenReturn(dealershipDTOs.get(0));
        when(carDealershipMapper.toDto(dealership2)).thenReturn(dealershipDTOs.get(1));

        // Act
        List<CarDealershipDTO> result = carDealershipService.findAllCarDealerships();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Dealership 1", result.get(0).name());
        assertEquals("Dealership 2", result.get(1).name());
        verify(carDealershipRepository, times(1)).findAll();
        verify(carDealershipMapper, times(2)).toDto(any(CarDealerships.class));
    }

    @Test
    void testGetCarDealershipById_DealershipExists() {
        // Arrange
        Long dealershipId = 1L;
        CarDealerships dealership = new CarDealerships();
        dealership.setDealershipId(dealershipId);
        dealership.setName("Dealership 1");

        CarDealershipDTO dealershipDTO = new CarDealershipDTO(dealershipId, "Dealership 1", null, "Logo1", "Type1", "Address1", 1L);

        when(carDealershipRepository.findById(dealershipId)).thenReturn(Optional.of(dealership));
        when(carDealershipMapper.toDto(dealership)).thenReturn(dealershipDTO);

        // Act
        CarDealershipDTO result = carDealershipService.getCarDealershipById(dealershipId);

        // Assert
        assertNotNull(result);
        assertEquals("Dealership 1", result.name());
        verify(carDealershipRepository, times(1)).findById(dealershipId);
        verify(carDealershipMapper, times(1)).toDto(dealership);
    }

    @Test
    void testGetCarDealershipById_DealershipNotFound() {
        // Arrange
        Long dealershipId = 1L;
        when(carDealershipRepository.findById(dealershipId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> carDealershipService.getCarDealershipById(dealershipId));
        assertEquals("Dealership not found with ID: " + dealershipId, exception.getMessage());
        verify(carDealershipRepository, times(1)).findById(dealershipId);
        verify(carDealershipMapper, never()).toDto(any(CarDealerships.class));
    }
}
