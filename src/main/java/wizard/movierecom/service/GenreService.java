package wizard.movierecom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wizard.movierecom.domain.Genre;
import wizard.movierecom.repository.GenreRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    public Long save(Genre genre) {
        validateDuplicateGenre(genre);
        genreRepository.save(genre);
        return genre.getId();
    }

    private void validateDuplicateGenre(Genre genre) {
        List<Genre> result = genreRepository.findGenreByName(genre.getName());
        if (!result.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 장르입니다.");
        }
    }
}
