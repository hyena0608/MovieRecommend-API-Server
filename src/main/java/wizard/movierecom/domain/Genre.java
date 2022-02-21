package wizard.movierecom.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
public class Genre {

    @Id @GeneratedValue
    @Column(name = "genre_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "genre")
    private List<MovieGenre> movieGenres;
}
