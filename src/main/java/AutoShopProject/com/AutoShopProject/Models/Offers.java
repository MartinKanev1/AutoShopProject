package AutoShopProject.com.AutoShopProject.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Table(name = "offers")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Offers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "posted_on")
    private Timestamp postedOn;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @Column(name = "brand")
    private String brand;

    @Column(name = "category")
    private String category;

    @Column(name = "model")
    private String model;

    @Column(name = "year_of_creation")
    private Integer yearOfCreation;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "mileage")
    private BigDecimal mileage;

    @Column(name = "gear")
    private String gear;

    @Column(name = "color")
    private String color;

    @Column(name = "power")
    private Integer power;

    @Column(name = "door_count")
    private Integer doorCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favourites> favoritesList;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reports> reportsList;

}
