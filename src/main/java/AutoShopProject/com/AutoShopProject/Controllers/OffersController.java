package AutoShopProject.com.AutoShopProject.Controllers;

import AutoShopProject.com.AutoShopProject.DTOs.OfferSearchCriteriaDTO;
import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.OffersRepository;
import AutoShopProject.com.AutoShopProject.Services.OffersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class OffersController {

    private final OffersService offersService;
    private final OffersRepository offersRepository;

    public OffersController(OffersService offersService,OffersRepository offersRepository) {
        this.offersService = offersService;
        this.offersRepository = offersRepository;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OffersDTO> createOffer(@PathVariable Long userId ,@RequestPart OffersDTO offerDTO, @RequestPart(value = "Image", required = false) MultipartFile Image) throws IOException {
        OffersDTO createdOffer = offersService.createOffer(userId, offerDTO, Image);
        return ResponseEntity.status(201).body(createdOffer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OffersDTO> updateOffer(@PathVariable Long id, @RequestPart OffersDTO offerDTO, @RequestPart(value = "Image", required = false) MultipartFile Image) throws IOException {
        OffersDTO updatedOffer = offersService.updateOffer(id, offerDTO,Image);
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



    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getOfferImage(@PathVariable Long id) {
        Offers offer = offersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + id + " not found"));

        if (offer.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(offer.getImageType()))
                .body(offer.getImageData());
    }


    @PostMapping("/{id}/report")
    public ResponseEntity<ReportsDTO> reportOffer(@PathVariable Long id, @RequestBody ReportsDTO reportDTO) {
        ReportsDTO createdReport = offersService.reportOffer(id, reportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }


    @PostMapping("/search")
    public ResponseEntity<List<OffersDTO>> searchOffers(
            @RequestParam Long userId, // Приемаме userId като request parameter
            @RequestBody OfferSearchCriteriaDTO criteria) {
        List<OffersDTO> results = offersService.searchOffers(userId, criteria);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}/owner")
    public  ResponseEntity<Long> getUserIdfromOfferId(@PathVariable Long id) {
        Offers offers = offersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        User user = offers.getUser(); // One-to-One relationship

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getUserId());
    }


       @GetMapping("allreports")
     public ResponseEntity<List<ReportsDTO>> getAllReports() {
     List<ReportsDTO> reports = offersService.getAllReports();
     return ResponseEntity.ok(reports);
   }

    @GetMapping("/{reportId}/offer")
    public ResponseEntity<?> getOfferIdByReportId(@PathVariable Long reportId) {
        Long offerId = offersService.getOfferIdByReportId(reportId);

        if (offerId != null) {
            return ResponseEntity.ok().body(offerId);
        } else {
            return ResponseEntity.status(404).body("{\"message\": \"No offer found for this report\"}");
        }
    }


}
