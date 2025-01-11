package AutoShopProject.com.AutoShopProject.Services;

import AutoShopProject.com.AutoShopProject.DTOs.OfferSearchCriteriaDTO;
import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.DTOs.ReportsDTO;
import AutoShopProject.com.AutoShopProject.Mappers.OffersMapper;
import AutoShopProject.com.AutoShopProject.Mappers.ReportsMapper;
import AutoShopProject.com.AutoShopProject.Models.Offers;
import AutoShopProject.com.AutoShopProject.Models.Reports;
import AutoShopProject.com.AutoShopProject.Models.SearchHistory;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.OffersRepository;
import AutoShopProject.com.AutoShopProject.Repositories.ReportsRepository;
import AutoShopProject.com.AutoShopProject.Repositories.SearchHistoryRepository;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Specifications.OffersSpecifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OffersService {

    private final OffersMapper offersMapper;
    private final OffersRepository offersRepository;
    private final UserRepository userRepository;
    private final ReportsRepository reportsRepository;
    private final SearchHistoryService searchHistoryService;
    private final SearchHistoryRepository searchHistoryRepository;

    private static final Logger log = LoggerFactory.getLogger(OffersService.class);

    public OffersService(OffersMapper offersMapper, OffersRepository offersRepository, UserRepository userRepository,ReportsRepository reportsRepository,SearchHistoryService searchHistoryService,SearchHistoryRepository searchHistoryRepository) {
        this.offersMapper = offersMapper;
        this.offersRepository = offersRepository;
        this.userRepository = userRepository;
        this.reportsRepository = reportsRepository;
        this.searchHistoryService = searchHistoryService;
        this.searchHistoryRepository = searchHistoryRepository;
    }



    @Transactional
    public OffersDTO createOffer(OffersDTO offerDTO) {

        log.info("Starting offer creation process with OffersDTO: {}", offerDTO);


        if (offerDTO.title() == null || offerDTO.title().isEmpty()) {
            log.error("Validation failed: Title is missing");
            throw new IllegalArgumentException("Title is required");
        }
        if (offerDTO.price() == null || offerDTO.price().compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Validation failed: Price is missing or invalid");
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (offerDTO.user() == null || offerDTO.user().email() == null) {
            log.error("Validation failed: User is missing or invalid");
            throw new IllegalArgumentException("Valid user information is required");
        }


        Optional<User> userOptional = userRepository.findByEmail(offerDTO.user().email());
        if (userOptional.isEmpty()) {
            log.error("User with email {} not found", offerDTO.user().email());
            throw new IllegalArgumentException("User with the provided email does not exist");
        }

        User user = userOptional.get();


        Offers offer = new Offers();
        offer.setTitle(offerDTO.title());
        offer.setDescription(offerDTO.description());
        offer.setStatus(offerDTO.status());
        offer.setPostedOn(Timestamp.valueOf(LocalDateTime.now()));
        offer.setPrice(offerDTO.price());
        offer.setBrand(offerDTO.brand());
        offer.setModel(offerDTO.model());
        offer.setYearOfCreation(offerDTO.yearOfCreation());
        offer.setFuel(offerDTO.fuel());
        offer.setMileage(offerDTO.mileage());
        offer.setColor(offerDTO.color());
        offer.setGear(offerDTO.gear());
        offer.setPower(offerDTO.power());
        offer.setDoorCount(offerDTO.doorCount());
        offer.setUser(user);

        log.info("Mapped Offers entity: {}", offer);


        Offers createdOffer = offersRepository.save(offer);
        log.info("Offer saved successfully with ID: {}", createdOffer.getOfferId());


        OffersDTO createdOfferDTO = offersMapper.toDTO(createdOffer);

        log.info("Successfully created OffersDTO: {}", createdOfferDTO);

        return createdOfferDTO;
    }

    @Transactional
    public OffersDTO updateOffer(Long offerId, OffersDTO offerDTO) {
        log.info("Starting update process for offer ID: {}", offerId);


        Offers existingOffer = offersRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));

        log.info("Found existing offer: {}", existingOffer);


        if (offerDTO.title() != null && !offerDTO.title().isEmpty()) {
            existingOffer.setTitle(offerDTO.title());
        }

        if (offerDTO.description() != null) {
            existingOffer.setDescription(offerDTO.description());
        }

        if (offerDTO.price() != null && offerDTO.price().compareTo(BigDecimal.ZERO) > 0) {
            existingOffer.setPrice(offerDTO.price());
        }

        if (offerDTO.brand() != null) {
            existingOffer.setBrand(offerDTO.brand());
        }

        if (offerDTO.model() != null) {
            existingOffer.setModel(offerDTO.model());
        }

        if (offerDTO.yearOfCreation() != null) {
            existingOffer.setYearOfCreation(offerDTO.yearOfCreation());
        }

        if (offerDTO.fuel() != null) {
            existingOffer.setFuel(offerDTO.fuel());
        }

        if (offerDTO.mileage() != null) {
            existingOffer.setMileage(offerDTO.mileage());
        }

        if (offerDTO.color() != null) {
            existingOffer.setColor(offerDTO.color());
        }

        if (offerDTO.gear() != null) {
            existingOffer.setGear(offerDTO.gear());
        }

        if (offerDTO.power() != null) {
            existingOffer.setPower(offerDTO.power());
        }

        if (offerDTO.doorCount() != null) {
            existingOffer.setDoorCount(offerDTO.doorCount());
        }

        log.info("Updated offer entity: {}", existingOffer);


        Offers updatedOffer = offersRepository.save(existingOffer);
        log.info("Offer updated successfully with ID: {}", updatedOffer.getOfferId());


        OffersDTO updatedOfferDTO = offersMapper.toDTO(updatedOffer);

        log.info("Successfully updated OffersDTO: {}", updatedOfferDTO);

        return updatedOfferDTO;
    }

    public List<OffersDTO> getAllOffers() {
        return offersRepository.findAll().stream().map(offersMapper::toDTO).collect(Collectors.toList());
    }

    public void deleteOffer(Long offerId) {



        Offers existingOffer = offersRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));
                offersRepository.deleteById(offerId);

    }

    public Optional<OffersDTO> getOfferById(Long offerId) {
        return offersRepository.findById(offerId).map(offersMapper::toDTO);
    }

    @Transactional
    public ReportsDTO reportOffer(Long offerId, ReportsDTO reportDTO) {
        log.info("Starting report process for offer ID: {}", offerId);


        if (reportDTO.reason() == null || reportDTO.reason().isEmpty()) {
            throw new IllegalArgumentException("Reason for the report is required");
        }
        if (reportDTO.userId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }


        Offers offer = offersRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));


        User user = userRepository.findById(reportDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + reportDTO.userId() + " not found"));


        Reports report = new Reports();
        report.setReason(reportDTO.reason());
        report.setReportedAt(Timestamp.valueOf(LocalDateTime.now()));
        report.setOffer(offer);
        report.setUser(user);


        Reports savedReport = reportsRepository.save(report);


        return new ReportsDTO(
                savedReport.getReportId(),
                savedReport.getUser().getUserId(),
                savedReport.getOffer().getOfferId(),
                savedReport.getReason(),
                savedReport.getReportedAt()
        );


    }

    public List<ReportsDTO> getReportsForOffer(Long offerId) {
        log.info("Fetching all reports for offer ID: {}", offerId);


        Offers offer = offersRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer with ID " + offerId + " not found"));


        List<Reports> reports = reportsRepository.findAllByOffer(offer);


        return reports.stream()
                .map(report -> new ReportsDTO(
                        report.getReportId(),
                        report.getUser().getUserId(),
                        report.getOffer().getOfferId(),
                        report.getReason(),
                        report.getReportedAt()
                ))
                .collect(Collectors.toList());
    }

   /* raboti
    public List<OffersDTO> searchOffers(OfferSearchCriteriaDTO criteria) {
        Specification<Offers> spec = Specification.where(
                        OffersSpecifications.hasBrand(criteria.brand()))
                .and(OffersSpecifications.hasModel(criteria.model()))
                .and(OffersSpecifications.hasFuel(criteria.fuel()))
                .and(OffersSpecifications.hasGear(criteria.gear()))
                .and(OffersSpecifications.hasColor(criteria.color()))
                .and(OffersSpecifications.hasDoors(criteria.doorCount()))
                .and(criteria.isDealer() != null ? OffersSpecifications.isByDealer(criteria.isDealer()) : null)
                .and(OffersSpecifications.priceBetween(criteria.minPrice(), criteria.maxPrice()))
                .and(OffersSpecifications.mileageBetween(criteria.minMileage(), criteria.maxMileage()))
                .and(OffersSpecifications.powerBetween(criteria.minPower(), criteria.maxPower()))
                .and(OffersSpecifications.yearBetween(criteria.minYear(), criteria.maxYear()));


        List<Offers> offers = offersRepository.findAll(spec, Sort.unsorted());



        return offers.stream()
                .map(offersMapper::toDTO)
                .collect(Collectors.toList());
    }

    */

    public List<OffersDTO> searchOffers(Long userId, OfferSearchCriteriaDTO criteria) {

        Specification<Offers> spec = Specification.where(
                        OffersSpecifications.hasBrand(criteria.brand()))
                .and(OffersSpecifications.hasModel(criteria.model()))
                .and(OffersSpecifications.hasFuel(criteria.fuel()))
                .and(OffersSpecifications.hasGear(criteria.gear()))
                .and(OffersSpecifications.hasColor(criteria.color()))
                .and(OffersSpecifications.hasDoors(criteria.doorCount()))
                .and(criteria.isDealer() != null ? OffersSpecifications.isByDealer(criteria.isDealer()) : null)
                .and(OffersSpecifications.priceBetween(criteria.minPrice(), criteria.maxPrice()))
                .and(OffersSpecifications.mileageBetween(criteria.minMileage(), criteria.maxMileage()))
                .and(OffersSpecifications.powerBetween(criteria.minPower(), criteria.maxPower()))
                .and(OffersSpecifications.yearBetween(criteria.minYear(), criteria.maxYear()));


        List<Offers> offers = offersRepository.findAll(spec, Sort.unsorted());


        List<OffersDTO> offersDTOList = offers.stream()
                .map(offersMapper::toDTO)
                .collect(Collectors.toList());


        saveSearchHistory(userId, criteria);

        return offersDTOList;
    }

    private void saveSearchHistory(Long userId, OfferSearchCriteriaDTO criteria) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));


        String searchCriteriaJson = toJson(criteria);


        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setSearchedOn(Timestamp.valueOf(LocalDateTime.now()));
        searchHistory.setUser(user);
        searchHistory.setSearchCriteria(searchCriteriaJson);






        searchHistoryRepository.save(searchHistory);
    }



    private String toJson(OfferSearchCriteriaDTO criteria) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(criteria);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert search criteria to JSON", e);
        }
    }



}
