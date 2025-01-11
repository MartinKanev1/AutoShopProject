package AutoShopProject.com.AutoShopProject.ServicesTests;

import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.Mappers.OffersMapper;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.Reports;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.OffersRepository;
import AutoShopProject.com.AutoShopProject.Repositories.ReportsRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Services.OffersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class OffersServiceTest {
    @Mock
    private OffersMapper offersMapper;

    @Mock
    private OffersRepository offersRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReportsRepository reportsRepository;

    @InjectMocks
    private OffersService offersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }





    @Test
    void testDeleteOffer() {

        Long offerId = 1L;
        Offers offer = new Offers();
        offer.setOfferId(offerId);

        when(offersRepository.findById(offerId)).thenReturn(Optional.of(offer));


        offersService.deleteOffer(offerId);


        verify(offersRepository, times(1)).findById(offerId);
        verify(offersRepository, times(1)).deleteById(offerId);
    }

    @Test
    void testReportOffer() {

        Long offerId = 1L;
        ReportsDTO reportDTO = new ReportsDTO(null, 1L, offerId, "Spam", null);

        Offers offer = new Offers();
        offer.setOfferId(offerId);

        User user = new User();
        user.setUserId(1L);

        Reports report = new Reports();
        report.setReportId(1L);
        report.setReason("Spam");
        report.setOffer(offer);
        report.setUser(user);
        report.setReportedAt(Timestamp.valueOf(LocalDateTime.now()));

        Reports savedReport = new Reports();
        savedReport.setReportId(1L);
        savedReport.setReason("Spam");
        savedReport.setOffer(offer);
        savedReport.setUser(user);
        savedReport.setReportedAt(Timestamp.valueOf(LocalDateTime.now()));

        when(offersRepository.findById(offerId)).thenReturn(Optional.of(offer));
        when(userRepository.findById(reportDTO.userId())).thenReturn(Optional.of(user));
        when(reportsRepository.save(any(Reports.class))).thenReturn(savedReport);


        ReportsDTO result = offersService.reportOffer(offerId, reportDTO);


        assertNotNull(result);
        assertEquals("Spam", result.reason());
        verify(offersRepository, times(1)).findById(offerId);
        verify(userRepository, times(1)).findById(reportDTO.userId());
        verify(reportsRepository, times(1)).save(any(Reports.class));
    }
}
