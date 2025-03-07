package AutoShopProject.com.AutoShopProject.ControllersTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import AutoShopProject.com.AutoShopProject.Controllers.OffersController;
import AutoShopProject.com.AutoShopProject.DTOs.OfferSearchCriteriaDTO;
import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.OffersRepository;
import AutoShopProject.com.AutoShopProject.Services.OffersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OffersControllerTest {

    @Mock
    private OffersService offersService;

    @Mock
    private OffersRepository offersRepository;

    @InjectMocks
    private OffersController offersController;

    private OffersDTO offerDTO;
    private Offers offer;

    @BeforeEach
    void setUp() {
        offer = new Offers();
        offer.setOfferId(1L);
        offer.setTitle("Test Offer");

        offerDTO = new OffersDTO(1L, "Test Offer", "Description", "Active", null,
                null, "Category", "Brand", "Model", 2022,
                "Fuel", null, "Color", "Gear", 200, 4, "image.jpg",
                "image/jpg",  new byte[0], 1L);
    }

    @Test
    void testCreateOffer_Success() throws IOException {
        MultipartFile image = mock(MultipartFile.class);
        when(offersService.createOffer(1L, offerDTO, image)).thenReturn(offerDTO);

        ResponseEntity<OffersDTO> response = offersController.createOffer(1L, offerDTO, image);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Test Offer", response.getBody().title());
    }

    @Test
    void testUpdateOffer_Success() throws IOException {
        MultipartFile image = mock(MultipartFile.class);
        when(offersService.updateOffer(1L, offerDTO, image)).thenReturn(offerDTO);

        ResponseEntity<OffersDTO> response = offersController.updateOffer(1L, offerDTO, image);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Offer", response.getBody().title());
    }

    @Test
    void testGetAllOffers() {
        List<OffersDTO> offers = List.of(offerDTO);
        when(offersService.getAllOffers()).thenReturn(offers);

        ResponseEntity<List<OffersDTO>> response = offersController.getAllOffers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(offers, response.getBody());
    }

    @Test
    void testDeleteOffer() {
        doNothing().when(offersService).deleteOffer(1L);

        ResponseEntity<Void> response = offersController.deleteOffer(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testGetOfferById() {
        when(offersService.getOfferById(1L)).thenReturn(Optional.of(offerDTO));

        ResponseEntity<Optional<OffersDTO>> response = offersController.getOfferById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isPresent());
        assertEquals("Test Offer", response.getBody().get().title());
    }

    @Test
    void testReportOffer() {
        ReportsDTO reportDTO = new ReportsDTO(1L, 1L, 1L, "Fraud", null);
        when(offersService.reportOffer(1L, reportDTO)).thenReturn(reportDTO);

        ResponseEntity<ReportsDTO> response = offersController.reportOffer(1L, reportDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Fraud", response.getBody().reason());
    }

    @Test
    void testSearchOffers() {
        OfferSearchCriteriaDTO criteria = mock(OfferSearchCriteriaDTO.class);
        List<OffersDTO> offers = List.of(offerDTO);
        when(offersService.searchOffers(1L, criteria)).thenReturn(offers);

        ResponseEntity<List<OffersDTO>> response = offersController.searchOffers(1L, criteria);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(offers, response.getBody());
    }

    @Test
    void testGetUserIdFromOfferId() {
        User user = new User();
        user.setUserId(1L);
        offer.setUser(user);
        when(offersRepository.findById(1L)).thenReturn(Optional.of(offer));

        ResponseEntity<Long> response = offersController.getUserIdfromOfferId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testGetOfferIdByReportId() {
        when(offersService.getOfferIdByReportId(1L)).thenReturn(1L);

        ResponseEntity<?> response = offersController.getOfferIdByReportId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody());
    }
}
