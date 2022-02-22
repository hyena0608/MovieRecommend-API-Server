package wizard.movierecom.domain;

import javax.persistence.*;

@Entity
@Table(name = "movie_genre")
public class MovieGenre {

    @Id @GeneratedValue
    @Column(name = "movie_genre_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public MovieGenre() {

    }

    public MovieGenre(Movie movie, Genre genre) {
        this.movie = movie;
        this.genre = genre;
    }

    public static MovieGenre createMovieGenre(Movie movie, Genre genre) {
        return new MovieGenre(movie, genre);
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
