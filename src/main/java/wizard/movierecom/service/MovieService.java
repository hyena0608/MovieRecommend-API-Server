package wizard.movierecom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wizard.movierecom.domain.Movie;
import wizard.movierecom.repository.MovieRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public Long save(Movie movie) {
        validateDuplicateMovie(movie);
        movieRepository.save(movie);
        return movie.getId();
    }


    private void validateDuplicateMovie(Movie movie) {
        List<Movie> result = movieRepository.findByName(movie.getTitle());
        if (!result.isEmpty()) {
            throw new IllegalStateException("영화 중복");
        }
    }



}
