package alkemy.challenge.Challenge.Alkemy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizations")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    private String address;

    @Positive
    private Integer phone;

    @NotBlank
    private String email;

    @NotBlank
    private String welcomeText;

    private String aboutUsText;

    private String linkdnUrl;

    private String facebookUrl;

    private String instagramUrl;

    private boolean deleted = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Organization(@NotNull String name, @NotNull String image, String address, Integer phone, @NotNull String email, @NotNull String welcomeText, String aboutUsText) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
    }

    public Organization(long Id, String ongName, String image, String address, int i, String s, String welcomeText, String aboutUsText) {
    }
}
