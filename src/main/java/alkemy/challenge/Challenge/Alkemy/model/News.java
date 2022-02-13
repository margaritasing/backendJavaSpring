package alkemy.challenge.Challenge.Alkemy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_news")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name = "news_category_id")
    private Category categoryId;

    private boolean deleted = false;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public News(String name, String image, String content) {
        this.name = name;
        this.image = image;
        this.content = content;
    }

    public News(String name, String image, String content, Category categoryId) {
        this.name = name;
        this.image = image;
        this.content = content;
        this.categoryId = categoryId;
    }
}
