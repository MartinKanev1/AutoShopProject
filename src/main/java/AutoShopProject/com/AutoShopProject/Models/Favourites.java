package AutoShopProject.com.AutoShopProject.Models;

import jakarta.persistence.*;
import lombok.Data;



import java.sql.Timestamp;

@Data
@Table(name = "favourites")
@Entity
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "added_on", nullable = false, updatable = false)
    private Timestamp addedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offers offer;

}
