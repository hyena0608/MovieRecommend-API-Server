package wizard.movierecom.domain;

import javax.persistence.*;

@Entity
@Table(name = "movie_director")
public class MovieDirector {

    @Id @GeneratedValue
    @Column(name = "movie_director_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Director director;
}
