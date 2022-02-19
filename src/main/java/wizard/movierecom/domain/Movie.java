package wizard.movierecom.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "movie_id")
    private Long id;

    private String title;
    private String content;
    private String playdate;
    private String country;

//    @Enumerated(EnumType.STRING)
//    private MovieLimitAge limitAge;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieDirector> movieDirectors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieActor> movieActors;

    public Movie(String title, String playdate, String country) {
        this.title = title;
        this.playdate = playdate;
        this.country = country;
    }

    public Movie() {

    }
}
