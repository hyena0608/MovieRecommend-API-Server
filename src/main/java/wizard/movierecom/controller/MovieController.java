package wizard.movierecom.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import wizard.movierecom.domain.MovieGenre;
import wizard.movierecom.repository.GenreRepository;
import wizard.movierecom.repository.MovieRepository;
import wizard.movierecom.domain.Movie;
import wizard.movierecom.repository.MovieSearch;
import wizard.movierecom.service.GenreService;
import wizard.movierecom.service.MovieService;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieRepository movieRepository;
    private final MovieService movieService;

    @PostMapping("/api")
    public void createMovie(@RequestBody Movie movie) {
        Movie movie1 = new Movie(movie.getTitle(), movie.getRelease_date(), movie.getCountry());
        movieRepository.save(movie1);

    }

    @GetMapping("/api/search/movie")
    public List<Movie> findMovieByString(String title, @Nullable String content) {
        List<Movie> result = movieRepository.findAllByString(new MovieSearch(title, content));
        return result;
    }

    @GetMapping("/api/search/genre")
    public List<MovieDto> findMovieByGenre(String name) {
        List<Movie> movies = movieRepository.findByGenre(name);
        List<MovieDto> result = movies.stream()
                .map(o -> new MovieDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @Getter
    static class MovieDto {
        private String title;
        private String overview;
        private String release_date;
        private int runtime;
        private float vote_average;
        private int vote_count;
        private List<MovieGenreDto> movieGenres;

        public MovieDto(Movie movie) {
            title = movie.getTitle();
            overview = movie.getOverview();
            release_date = movie.getRelease_date();
            runtime = movie.getRuntime();
            vote_average = movie.getVote_average();
            vote_count = movie.getVote_count();
            movieGenres = movie.getMovieGenres().stream()
                    .map(movieGenre -> new MovieGenreDto(movieGenre))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    static class MovieGenreDto {
        private String genreName;

        public MovieGenreDto(MovieGenre movieGenre) {
            genreName = movieGenre.getGenre().getName();
        }
    }
}
