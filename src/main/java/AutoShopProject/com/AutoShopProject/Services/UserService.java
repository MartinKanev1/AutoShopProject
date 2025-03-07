package AutoShopProject.com.AutoShopProject.Services;

import AutoShopProject.com.AutoShopProject.DTOs.OffersDTO;
import AutoShopProject.com.AutoShopProject.Mappers.CarDealershipMapper;
import AutoShopProject.com.AutoShopProject.Mappers.OffersMapper;
import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.SellerType;
import AutoShopProject.com.AutoShopProject.Repositories.CarDealershipRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.DTOs.UserDTO;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import AutoShopProject.com.AutoShopProject.Mappers.UserMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CarDealershipRepository carDealershipRepository;
    private final UserMapper userMapper;
    private final CarDealershipMapper carDealershipMapper;
    private final OffersMapper offersMapper;

    public UserService(UserRepository userRepository,
                       CarDealershipRepository carDealershipRepository,
                       UserMapper userMapper,
                       CarDealershipMapper carDealershipMapper,
                       OffersMapper offersMapper) {
        this.userRepository = userRepository;
        this.carDealershipRepository = carDealershipRepository;
        this.userMapper = userMapper;
        this.carDealershipMapper = carDealershipMapper;
        this.offersMapper = offersMapper;
    }

    // Вземане на потребител по ID
    public Optional<UserDTO> getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(userMapper::toDto);
    }

    public Long getUserIdFromEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getUserId) // Extract userId if present
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email)); // Throw error if missing
    }



    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }




    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    //-raboti!!!

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        log.info("Starting user creation process with UserDTO: {}", userDTO);


        if (userDTO.firstName() == null || userDTO.firstName().isEmpty()) {
            log.error("Validation failed: First name is missing");
            throw new IllegalArgumentException("First name is required");
        }
        if (userDTO.lastName() == null || userDTO.lastName().isEmpty()) {
            log.error("Validation failed: Last name is missing");
            throw new IllegalArgumentException("Last name is required");
        }
        if (userDTO.email() == null || userDTO.email().isEmpty()) {
            log.error("Validation failed: Email is missing");
            throw new IllegalArgumentException("Email is required");
        }
        if (userDTO.type() == null) {
            log.error("Validation failed: User type is missing");
            throw new IllegalArgumentException("User type is required");
        }

        log.info("Validation passed for UserDTO: {}", userDTO);

        User user = new User();
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setPhoneNumber(userDTO.phoneNumber());
        user.setRegion(userDTO.region());
        user.setCity(userDTO.city());
        user.setType(userDTO.type());

        log.info("Mapped User entity: {}", user);


        if (userDTO.type() == SellerType.CAR_DEALERSHIP) {
            log.info("User type is CAR_DEALERSHIP, processing car dealership details");

            if (userDTO.carDealership() == null) {
                log.error("Car dealership details are required for CAR_DEALERSHIP users");
                throw new IllegalArgumentException("Car dealership details are required for CAR_DEALERSHIP users");
            }

            CarDealerships carDealership = carDealershipMapper.toEntity(userDTO.carDealership());
            if (carDealership == null) {
                log.error("Failed to map CarDealership details from UserDTO");
                throw new IllegalStateException("Failed to map CarDealership details");
            }

            log.info("Mapped CarDealership entity: {}", carDealership);

            carDealership.setUser(user);
            user.setCarDealership(carDealership);

            log.info("Linked CarDealership to User");
        }


        log.info("Saving User entity to the database: {}", user);
        User createdUser = userRepository.save(user);
        log.info("User saved successfully with ID: {}", createdUser.getUserId());


        UserDTO createdUserDTO = userMapper.toDto(createdUser);
        if (createdUserDTO == null) {
            log.error("Failed to map created User entity to UserDTO");
            throw new IllegalStateException("Failed to map created User entity to DTO");
        }

        log.info("Successfully created UserDTO: {}", createdUserDTO);

        return createdUserDTO;
    }







    @Transactional
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO, MultipartFile logoImage) throws IOException {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));


        existingUser.setFirstName(updatedUserDTO.firstName());
        existingUser.setLastName(updatedUserDTO.lastName());
        existingUser.setEmail(updatedUserDTO.email());
        existingUser.setPassword(updatedUserDTO.password());
        existingUser.setPhoneNumber(updatedUserDTO.phoneNumber());
        existingUser.setRegion(updatedUserDTO.region());
        existingUser.setCity(updatedUserDTO.city());
        existingUser.setType(updatedUserDTO.type());


        if (updatedUserDTO.type() == SellerType.CAR_DEALERSHIP) {
            if (updatedUserDTO.carDealership() == null) {
                throw new IllegalArgumentException("Car dealership details are required for CAR_DEALERSHIP users");
            }

            CarDealerships existingDealership = existingUser.getCarDealership();

            if (existingDealership != null) {

                CarDealerships updatedDealership = carDealershipMapper.toEntity(updatedUserDTO.carDealership());

                existingDealership.setName(updatedDealership.getName());
                existingDealership.setDateOfCreation(updatedDealership.getDateOfCreation());
                existingDealership.setAddress(updatedDealership.getAddress());


                if (logoImage != null && !logoImage.isEmpty()) {
                    existingDealership.setLogoImageName(logoImage.getOriginalFilename());
                    existingDealership.setLogoImageType(logoImage.getContentType());
                    existingDealership.setLogoImageData(logoImage.getBytes());

                    System.out.println("Saving Image: " + logoImage.getOriginalFilename() + " (" + logoImage.getSize() + " bytes)");
                } else {
                    System.out.println("No new image provided, keeping old image.");
                }


                carDealershipRepository.save(existingDealership);

            } else {

                CarDealerships newDealership = carDealershipMapper.toEntity(updatedUserDTO.carDealership());
                newDealership.setUser(existingUser);


                if (logoImage != null && !logoImage.isEmpty()) {
                    newDealership.setLogoImageName(logoImage.getOriginalFilename());
                    newDealership.setLogoImageType(logoImage.getContentType());
                    newDealership.setLogoImageData(logoImage.getBytes());

                    System.out.println("Saving New Image: " + logoImage.getOriginalFilename() + " (" + logoImage.getSize() + " bytes)");
                }


                carDealershipRepository.save(newDealership);
                existingUser.setCarDealership(newDealership);
            }
        } else {

            if (existingUser.getCarDealership() != null) {
                carDealershipRepository.delete(existingUser.getCarDealership());
                existingUser.setCarDealership(null);
            }
        }

        User savedUser = userRepository.save(existingUser);
        return userMapper.toDto(savedUser);
    }






    @Transactional
    public void deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));


        if (existingUser.getCarDealership() != null) {
            carDealershipRepository.delete(existingUser.getCarDealership());
        }


        userRepository.delete(existingUser);
    }


    public List<OffersDTO> getOffersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        return user.getOffers().stream().map(offersMapper::toDTO).collect(Collectors.toList());



    }






}
