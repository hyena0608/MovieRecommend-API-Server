package wizard.movierecom.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Column(columnDefinition = "TEXT")
    private String overview;
    private String release_date;
    private int runtime;
    private float vote_average;
    private int vote_count;
    private String country;
    private String tagline;

//    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
//    private List<MovieDirector> movieDirectors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieGenre> movieGenres = new ArrayList<>();

    public Movie(String title, String release_date, String country) {
        this.title = title;
        this.release_date = release_date;
        this.country = country;
    }

    public Movie(String title, String overview, String release_date, int runtime, float vote_average, int vote_count) {
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.runtime = runtime;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public Movie() {

    }

    /**
     * 연관관계 메서드
     */
    public void addMovieGenres(MovieGenre movieGenre) {
        movieGenres.add(movieGenre);
        movieGenre.setMovie(this);
    }

    /**
     * 생성 메서드
     */
    public static Movie createMovie(Movie movie, List<MovieGenre> movieGenres) {
        Movie movie1 = new Movie(movie.getTitle(), movie.getOverview(), movie.getRelease_date(), movie.getRuntime(), movie.getVote_average(), movie.getVote_count());
        for (MovieGenre movieGenre : movieGenres) {
            movie1.addMovieGenres(movieGenre);
        }
        return movie1;
    }

}
