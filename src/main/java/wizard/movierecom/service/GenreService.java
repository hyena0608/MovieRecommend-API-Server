package wizard.movierecom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wizard.movierecom.domain.Genre;
import wizard.movierecom.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    public Long save(Genre genre) {
        boolean isTrue = validateDuplicateGenre(genre);
        if (isTrue) {
            genreRepository.save(genre);
            return genre.getId();
        } else {
            return 0L;
        }
    }

    private boolean validateDuplicateGenre(Genre genre) {
        List<Genre> result = genreRepository.findGenreByName(genre.getName());
        if (!result.isEmpty()) {
            return false;
        }
        return true;
    }
}
