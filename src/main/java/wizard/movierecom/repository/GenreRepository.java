package wizard.movierecom.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wizard.movierecom.domain.Genre;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreRepository {

    private final EntityManager em;

    @Transactional
    public void save(Genre genre) {
        em.persist(genre);
    }

    public List<Genre> findGenreByName(String name) {
        return em.createQuery(
                        "select g from Genre g where g.name = :name", Genre.class
                ).setParameter("name", name)
                .getResultList();
    }

    public Genre findById(Long id) {
        return em.find(Genre.class, id);
    }

}


