package AutoShopProject.com.AutoShopProject.Controllers;

import AutoShopProject.com.AutoShopProject.DTOs.OfferSearchCriteriaDTO;
import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.Services.OffersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OffersController {

    private final OffersService offersService;

    public OffersController(OffersService offersService) {
        this.offersService = offersService;
    }

    @PostMapping
    public ResponseEntity<OffersDTO> createOffer(@RequestBody OffersDTO offerDTO) {
        OffersDTO createdOffer = offersService.createOffer(offerDTO);
        return ResponseEntity.status(201).body(createdOffer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OffersDTO> updateOffer(@PathVariable Long id, @RequestBody OffersDTO offerDTO) {
        OffersDTO updatedOffer = offersService.updateOffer(id, offerDTO);
        return ResponseEntity.ok(updatedOffer);
    }

    @GetMapping
    public ResponseEntity<List<OffersDTO>> getAllOffers() {
        List<OffersDTO> offers = offersService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offersService.deleteOffer(id);
        return ResponseEntity.noContent().build(); // Връща статус 204 No Content
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OffersDTO>> getOfferById(@PathVariable Long id) {
        Optional<OffersDTO> offerDTO = offersService.getOfferById(id);
        return ResponseEntity.ok(offerDTO);
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<ReportsDTO> reportOffer(@PathVariable Long id, @RequestBody ReportsDTO reportDTO) {
        ReportsDTO createdReport = offersService.reportOffer(id, reportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    /*
    @GetMapping("/{id}/reports")
    public ResponseEntity<List<ReportsDTO>> getReportsForOffer(@PathVariable Long id) {
        List<ReportsDTO> reports = offersService.getReportsForOffer(id);
        return ResponseEntity.ok(reports);
    }
    */


    /*работи
    @PostMapping("/search")
    public ResponseEntity<List<OffersDTO>> searchOffers(@RequestBody OfferSearchCriteriaDTO criteria) {
        List<OffersDTO> results = offersService.searchOffers(criteria);
        return ResponseEntity.ok(results);
    }
    */
    @PostMapping("/search")
    public ResponseEntity<List<OffersDTO>> searchOffers(
            @RequestParam Long userId, // Приемаме userId като request parameter
            @RequestBody OfferSearchCriteriaDTO criteria) {
        List<OffersDTO> results = offersService.searchOffers(userId, criteria);
        return ResponseEntity.ok(results);
    }



}
