package AutoShopProject.com.AutoShopProject.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "car_dealerships")
@NoArgsConstructor
public class CarDealerships {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealershipId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date_of_creation", nullable = false)
    private Timestamp dateOfCreation;

    @Column(name = "logo_image_name")
    private String logoImageName;

    @Column(name = "logo_image_type")
    private String logoImageType;

    @Lob
    @Column(name = "logo_image_data")
    private byte[] logoImageData;

    @Column(name = "address")
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @Override
    public String toString() {
        return "CarDealership{" +
                "dealershipId=" + dealershipId +
                ", name='" + name + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", logoImageName='" + logoImageName + '\'' +
                '}';
    }





}