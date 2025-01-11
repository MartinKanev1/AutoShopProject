package AutoShopProject.com.AutoShopProject.ControllersTests;

import AutoShopProject.com.AutoShopProject.Controllers.OffersController;
import AutoShopProject.com.AutoShopProject.DTOs.OfferSearchCriteriaDTO;
import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.Services.OffersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OffersControllerTest {

    @Mock
    private OffersService offersService;

    @InjectMocks
    private OffersController offersController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(offersController).build();
    }

    @Test
    void testCreateOffer() throws Exception {

        OffersDTO offerDTO = new OffersDTO(null, "Title", "Description", "ACTIVE", null, BigDecimal.valueOf(1000), "Brand", "Model", 2022, "Diesel", BigDecimal.valueOf(20000), "Black", "Manual", 150, 4, null);
        when(offersService.createOffer(any(OffersDTO.class))).thenReturn(offerDTO);


        mockMvc.perform(post("/api/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"Title\"," +
                                "\"description\":\"Description\"," +
                                "\"status\":\"ACTIVE\"," +
                                "\"price\":1000," +
                                "\"brand\":\"Brand\"," +
                                "\"model\":\"Model\"," +
                                "\"yearOfCreation\":2022," +
                                "\"fuel\":\"Diesel\"," +
                                "\"mileage\":20000," +
                                "\"color\":\"Black\"," +
                                "\"gear\":\"Manual\"," +
                                "\"power\":150," +
                                "\"doorCount\":4}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.price", is(1000)));

        verify(offersService, times(1)).createOffer(any(OffersDTO.class));
    }

    @Test
    void testUpdateOffer() throws Exception {

        Long offerId = 1L;
        OffersDTO offerDTO = new OffersDTO(null, "Title", "Description", "ACTIVE", null, BigDecimal.valueOf(1000), "Brand", "Model", 2022, "Diesel", BigDecimal.valueOf(20000), "Black", "Manual", 150, 4, null);
        when(offersService.updateOffer(eq(offerId), any(OffersDTO.class))).thenReturn(offerDTO);


        mockMvc.perform(put("/api/offers/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"title\":\"Updated Title\"," +
                                "\"description\":\"Updated Description\"," +
                                "\"status\":\"ACTIVE\"," +
                                "\"price\":1500," +
                                "\"brand\":\"Updated Brand\"," +
                                "\"model\":\"Updated Model\"," +
                                "\"yearOfCreation\":2023," +
                                "\"fuel\":\"Petrol\"," +
                                "\"mileage\":10000," +
                                "\"color\":\"Red\"," +
                                "\"gear\":\"Automatic\"," +
                                "\"power\":180," +
                                "\"doorCount\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.price", is(1500)));

        verify(offersService, times(1)).updateOffer(eq(offerId), any(OffersDTO.class));
    }

    @Test
    void testGetAllOffers() throws Exception {

        List<OffersDTO> offers = List.of(
                new OffersDTO(null, "Title", "Description", "ACTIVE", null, BigDecimal.valueOf(1000), "Brand", "Model", 2022, "Diesel", BigDecimal.valueOf(20000), "Black", "Manual", 150, 4, null)
        );
        when(offersService.getAllOffers()).thenReturn(offers);


        mockMvc.perform(get("/api/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Title 1")))
                .andExpect(jsonPath("$[1].title", is("Title 2")));

        verify(offersService, times(1)).getAllOffers();
    }

    @Test
    void testDeleteOffer() throws Exception {

        Long offerId = 1L;
        doNothing().when(offersService).deleteOffer(offerId);


        mockMvc.perform(delete("/api/offers/{id}", offerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(offersService, times(1)).deleteOffer(offerId);
    }

    @Test
    void testReportOffer() throws Exception {

        Long offerId = 1L;
        ReportsDTO reportDTO = new ReportsDTO(null, 1L, offerId, "Spam", null);
        when(offersService.reportOffer(eq(offerId), any(ReportsDTO.class))).thenReturn(reportDTO);


        mockMvc.perform(post("/api/offers/{id}/report", offerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"reason\":\"Spam\"," +
                                "\"userId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reason", is("Spam")));

        verify(offersService, times(1)).reportOffer(eq(offerId), any(ReportsDTO.class));
    }
}

