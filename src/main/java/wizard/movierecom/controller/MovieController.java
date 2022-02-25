package wizard.movierecom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import wizard.movierecom.repository.GenreRepository;
import wizard.movierecom.repository.MovieRepository;
import wizard.movierecom.domain.Movie;
import wizard.movierecom.repository.MovieSearch;
import wizard.movierecom.service.GenreService;
import wizard.movierecom.service.MovieService;

import java.util.List;


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
    public List<Movie> findMovieByGenre(String name) {
        List<Movie> result = movieRepository.findByGenre(name);
        return result;
    }
}
