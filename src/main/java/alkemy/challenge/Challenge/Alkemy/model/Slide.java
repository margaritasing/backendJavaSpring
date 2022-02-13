package alkemy.challenge.Challenge.Alkemy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "slides")
public class Slide implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_slides")
    private Long id;

    private String imageUrl;

    private String text;

    private Integer sequence;

    @ManyToOne
    @JoinColumn(name = "slide_organization")
    private Organization organizationId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Slide(String imageUrl, String text, Integer sequence, Organization organizationId) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.sequence = sequence;
        this.organizationId = organizationId;
    }
}
