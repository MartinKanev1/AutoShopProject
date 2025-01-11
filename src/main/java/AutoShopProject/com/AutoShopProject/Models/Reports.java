package AutoShopProject.com.AutoShopProject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;


import java.sql.Timestamp;

@Data
@Entity
@jakarta.persistence.Table(name = "reports")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    @JsonBackReference 
    private Offers offer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String reason;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp reportedAt;
}
