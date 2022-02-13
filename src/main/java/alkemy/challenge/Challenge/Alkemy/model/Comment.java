package alkemy.challenge.Challenge.Alkemy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comments")
    private Long id;

    private String body;

    @ManyToOne
    @JoinColumn(name = "comments_user")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "comments_news")
    private News newsId;

    public Comment(String body, User userId, News newsId) {
        this.body = body;
        this.userId = userId;
        this.newsId = newsId;
    }
}
