package AutoShopProject.com.AutoShopProject.Services;

import AutoShopProject.com.AutoShopProject.DTOs.CarDealershipDTO;

import AutoShopProject.com.AutoShopProject.Mappers.CarDealershipMapper;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;

import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDealershipService {

    private final CarDealershipRepository carDealershipRepository;
    private final CarDealershipMapper carDealershipMapper;

    public CarDealershipService(
            CarDealershipRepository carDealershipRepository,
            CarDealershipMapper carDealershipMapper
    ) {
       this.carDealershipMapper= carDealershipMapper;
       this.carDealershipRepository = carDealershipRepository;
    }


    public List<CarDealershipDTO> findAllCarDealerships() {
        List<CarDealerships> carDealerships = carDealershipRepository.findAll();
        return carDealerships.stream()
                .map(carDealershipMapper::toDto)
                .collect(Collectors.toList());
    }

    public CarDealershipDTO getCarDealershipById(Long dealershipId) {
        CarDealerships dealership = carDealershipRepository.findById(dealershipId)
                .orElseThrow(() -> new RuntimeException("Dealership not found with ID: " + dealershipId));
        return carDealershipMapper.toDto(dealership);
    }


}
