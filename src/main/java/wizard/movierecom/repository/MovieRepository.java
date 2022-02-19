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

    /**
     * 영화 넣기
     */
    @Transactional
    public void insertMovie(Movie movie) {
        em.persist(movie);
    }

    /**
     * 영화 찾기
     */
    public Movie findOneById(Long id) {
        Movie findMovie = em.find(Movie.class, id);
        return findMovie;
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
                ).setParameter("title", "%"+movieSearch.getTitle()+"%")
                .setParameter("content", movieSearch.getContent())
                .getResultList();

        System.out.println("resultList = " + resultList);
        return resultList;
    }


}
