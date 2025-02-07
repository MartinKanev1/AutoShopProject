package AutoShopProject.com.AutoShopProject.Security;

import AutoShopProject.com.AutoShopProject.Models.CarDealerships;
import AutoShopProject.com.AutoShopProject.Models.Roles;
import AutoShopProject.com.AutoShopProject.Models.SellerType;
import AutoShopProject.com.AutoShopProject.Models.User;
import AutoShopProject.com.AutoShopProject.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {

        if (request.getEmail() == null || request.getPassword() == null || request.getPhoneNumber() == null || request.getType() == null) {
            throw new IllegalArgumentException("Required fields are missing!");
        }


        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .region(request.getRegion())
                .city(request.getCity())
                .type(request.getType())
                .role(Roles.Dealer)
                .build();


        if (request.getType() == SellerType.CAR_DEALERSHIP && request.getCarDealership() != null) {
            CarDealerships dealership = new CarDealerships();
            dealership.setName(request.getCarDealership().getName());
            dealership.setDateOfCreation(request.getCarDealership().getDateOfCreation());
            dealership.setLogoImageName(request.getCarDealership().getLogoImageName());
            dealership.setAddress(request.getCarDealership().getAddress());
            dealership.setLogoImageType(request.getCarDealership().getLogoImageType());

            dealership.setUser(user); // ВАЖНО! Свързваме CarDealerships с User
            user.setCarDealership(dealership); // Свързваме User с CarDealerships
        }


        userRepository.save(user);


        String jwtToken = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        String jwtToken = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(jwtToken);
    }


}