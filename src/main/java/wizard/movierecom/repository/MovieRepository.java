package wizard.movierecom.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wizard.movierecom.domain.Movie;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieRepository {

    private final EntityManager em;

    @Transactional
    public void save(Movie movie) {
        em.persist(movie);
    }

    public Movie findOneById(Long id) {
        return em.find(Movie.class, id);
    }

    public List<Movie> findByName(String title) {
        List<Movie> findMovieList = em.createQuery(
                        "select m from Movie m where m.title = :title", Movie.class
                ).setParameter("title", title)
                .getResultList();
        return findMovieList;
    }

    public List<Movie> findAllByString(MovieSearch movieSearch) {
        String jpql = "select m From Movie m";
        boolean isFirstCondition = false;


        if (movieSearch.getTitle() != null) {
            jpql += " where" +
                    " m.title like :title";
            isFirstCondition = true;
        }
        if (movieSearch.getContent() != null) {
            if (isFirstCondition) {
                jpql += " or";
            } else {
                jpql += " where";
            }
            jpql += " m.content like :content";
        }

        List<Movie> resultList = em.createQuery(
                        jpql, Movie.class
                ).setParameter("title", "%" + movieSearch.getTitle() + "%")
                .setParameter("content", movieSearch.getContent())
                .getResultList();

        System.out.println("resultList = " + resultList);
        return resultList;
    }

    public List<Movie> findByGenre(String name) {
        List<Movie> result = em.createQuery(
                        "select distinct m" +
                                " from Movie m" +
                                " join fetch m.movieGenres mg" +
                                " join fetch mg.genre g" +
                                " where g.name like :name", Movie.class
                ).setParameter("name", "%" + name + "%")
                .getResultList();

        return result;
    }


}
