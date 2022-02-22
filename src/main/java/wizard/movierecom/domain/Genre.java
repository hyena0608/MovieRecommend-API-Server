package wizard.movierecom.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter
public class Genre {

    @Id @GeneratedValue
    @Column(name = "genre_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "genre")
    private List<MovieGenre> movieGenres;

    public Genre(String name) {
        this.name = name;
    }

    public Genre() {

    }
}
