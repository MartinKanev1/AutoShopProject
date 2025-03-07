package AutoShopProject.com.AutoShopProject.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.Mappers.OffersMapper;
import AutoShopProject.com.AutoShopProject.Mappers.ReportsMapper;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.Reports;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.OffersRepository;
import AutoShopProject.com.AutoShopProject.Repositories.ReportsRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.OffersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class OffersServiceTest {

    @Mock
    private OffersRepository offersRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReportsRepository reportsRepository;

    @Mock
    private OffersMapper offersMapper;

    @Mock
    private ReportsMapper reportsMapper;

    @InjectMocks
    private OffersService offersService;

    private Offers offer;
    private OffersDTO offerDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);

        offer = new Offers();
        offer.setOfferId(1L);
        offer.setTitle("Test Offer");
        offer.setPrice(BigDecimal.valueOf(10000));
        offer.setPostedOn(Timestamp.valueOf(LocalDateTime.now()));
        offer.setUser(user);

        offerDTO = new OffersDTO(
                1L, "Test Offer", "Description", "Active", Timestamp.valueOf(LocalDateTime.now()),
                BigDecimal.valueOf(10000), "Category", "Brand", "Model", 2022,
                "Fuel", BigDecimal.valueOf(10000), "Color", "Gear", 200, 4, "image.jpg",
                "image/jpg", new byte[0],1L
        );
    }

    @Test
    void testCreateOffer_Success() throws IOException {
        MultipartFile image = mock(MultipartFile.class);
        when(image.getOriginalFilename()).thenReturn("image.jpg");
        when(image.getContentType()).thenReturn("image/jpeg");
        when(image.getBytes()).thenReturn(new byte[10]);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(offersRepository.save(any(Offers.class))).thenReturn(offer);
        when(offersMapper.toDTO(any(Offers.class))).thenReturn(offerDTO);

        OffersDTO result = offersService.createOffer(1L, offerDTO, image);

        assertNotNull(result);
        assertEquals("Test Offer", result.title());
    }

    @Test
    void testCreateOffer_UserNotFound() {
        MultipartFile image = mock(MultipartFile.class);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                offersService.createOffer(1L, offerDTO, image)
        );

        assertEquals("User with the provided ID does not exist", exception.getMessage());
    }

    @Test
    void testGetOfferById_OfferExists() {
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(offersMapper.toDTO(offer)).thenReturn(offerDTO);

        Optional<OffersDTO> result = offersService.getOfferById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Offer", result.get().title());
    }

    @Test
    void testGetOfferById_OfferNotFound() {
        when(offersRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<OffersDTO> result = offersService.getOfferById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteOffer_OfferExists() {
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));
        doNothing().when(offersRepository).deleteById(1L);

        assertDoesNotThrow(() -> offersService.deleteOffer(1L));
        verify(offersRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteOffer_OfferNotFound() {
        when(offersRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> offersService.deleteOffer(1L));
        assertEquals("Offer with ID 1 not found", exception.getMessage());
    }

    @Test
    void testReportOffer_Success() {
        Reports report = new Reports();
        report.setReportId(1L);
        report.setReason("Fraud");
        report.setReportedAt(Timestamp.valueOf(LocalDateTime.now()));
        report.setOffer(offer);
        report.setUser(user);

        ReportsDTO reportDTO = new ReportsDTO(1L, 1L, 1L, "Fraud", Timestamp.valueOf(LocalDateTime.now()));

        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reportsRepository.save(any(Reports.class))).thenReturn(report);

        ReportsDTO result = offersService.reportOffer(1L, reportDTO);

        assertNotNull(result);
        assertEquals("Fraud", result.reason());
    }

    @Test
    void testReportOffer_OfferNotFound() {
        ReportsDTO reportDTO = new ReportsDTO(1L, 1L, 1L, "Fraud", Timestamp.valueOf(LocalDateTime.now()));
        when(offersRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                offersService.reportOffer(1L, reportDTO)
        );

        assertEquals("Offer with ID 1 not found", exception.getMessage());
    }
}
