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
    private String overview;
    private String release_date;
    private int runtime;
    private float vote_average;
    private int vote_count;
    private String country;

//    @Enumerated(EnumType.STRING)
//    private MovieLimitAge limitAge;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieDirector> movieDirectors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieActor> movieActors;

    public Movie(String title, String release_date, String country) {
        this.title = title;
        this.release_date = release_date;
        this.country = country;
    }

    public Movie() {

    }
}
